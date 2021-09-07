package com.techelevator.tenmo;

import com.techelevator.tenmo.model.*;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.AuthenticationServiceException;
import com.techelevator.tenmo.services.TransferService;
import com.techelevator.view.ConsoleService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";

    private static final String MENU_OPTION_EXIT = "Exit";
    private static final String LOGIN_MENU_OPTION_REGISTER = "Register";
    private static final String LOGIN_MENU_OPTION_LOGIN = "Login";
    private static final String[] LOGIN_MENU_OPTIONS = {LOGIN_MENU_OPTION_REGISTER, LOGIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT};
    private static final String MAIN_MENU_OPTION_VIEW_BALANCE = "View your current balance";
    private static final String MAIN_MENU_OPTION_SEND_BUCKS = "Send TE bucks";
    private static final String MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS = "View your past transfers";
    private static final String MAIN_MENU_OPTION_REQUEST_BUCKS = "Request TE bucks";
    private static final String MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS = "View your pending requests";
    private static final String MAIN_MENU_OPTION_LOGIN = "Login as different user";
    private static final String[] MAIN_MENU_OPTIONS = {MAIN_MENU_OPTION_VIEW_BALANCE, MAIN_MENU_OPTION_SEND_BUCKS, MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS, MAIN_MENU_OPTION_REQUEST_BUCKS, MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS, MAIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT};

    private AuthenticatedUser currentUser;
    private ConsoleService console;
    private AuthenticationService authenticationService;
    private RestTemplate restTemplate;
    private TransferService ts;

    public static void main(String[] args) {
        App app = new App(new ConsoleService(System.in, System.out), new AuthenticationService(API_BASE_URL));
        app.run();
    }

    public App(ConsoleService console, AuthenticationService authenticationService) {
        this.console = console;
        this.authenticationService = authenticationService;

        this.restTemplate = new RestTemplate();
    }

    public void run() {
        System.out.println("*********************");
        System.out.println("* Welcome to TEnmo! *");
        System.out.println("*********************");

        registerAndLogin();
        mainMenu();
    }

    private void mainMenu() {
        while (true) {
            String choice = (String) console.getChoiceFromOptions(MAIN_MENU_OPTIONS);
            if (MAIN_MENU_OPTION_VIEW_BALANCE.equals(choice)) {
                viewCurrentBalance();
            } else if (MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS.equals(choice)) {
                viewTransferHistory();
            } else if (MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS.equals(choice)) {
                viewPendingRequests();
            } else if (MAIN_MENU_OPTION_SEND_BUCKS.equals(choice)) {
                sendBucks();
            } else if (MAIN_MENU_OPTION_REQUEST_BUCKS.equals(choice)) {
                requestBucks();
            } else if (MAIN_MENU_OPTION_LOGIN.equals(choice)) {
                login();
            } else {
                // the only other option on the main menu is to exit
                exitProgram();
            }
        }
    }

    private void viewCurrentBalance() {
        // TODO Auto-generated method stub
        ts.viewCurrentBalance();

    }

    private void viewTransferHistory() {
        // TODO Auto-generated method stub
        //5
        Transfer[] listOfTransfers = ts.viewTransferHistory();

        for (Transfer transfer : listOfTransfers) {
            //if (userId == transfer.getAccountReceiver() || userId == transfer.getAccountSender()) { //trying to get transfers at userId
            System.out.println("Transfers Id: " + transfer.getTransferId());
            System.out.println("Sent From: " + transfer.getAccountSender());
            System.out.println("Send to: " + transfer.getAccountReceiver());
            System.out.println("Amount send: " + transfer.getAmount());
            System.out.println("******************");
        }
        System.out.println("Please enter transfer ID to view details (0 to cancel): ");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        boolean correctId = true;
        for (Transfer transfers : listOfTransfers) {
            if (Integer.parseInt(input) == transfers.getTransferId()) {
                System.out.println("Transfers Id: " + transfers.getTransferId());
                System.out.println("Sent From: " + transfers.getAccountSender());
                System.out.println("Send to: " + transfers.getAccountReceiver());
                System.out.println("Amount send: " + transfers.getAmount());
                System.out.println("******************");
            }
        }
            if (correctId = false) {
            System.out.println("Invalid ID");
        }
    }



    private void viewPendingRequests() {
        // TODO Auto-generated method stub

    }

    private void sendBucks() {
        // TODO Auto-generated method stub

        // 1. Display who you can send money to.
        User[] listOfRecipients = ts.retrieveAllUsersForDisplay();

        for (User user : listOfRecipients) {

            System.out.println(user.getId() + ": " + user.getUsername());
        }

        // 2. Use the console service to prompt  user who they want to send money to
        String accountTo = console.getUserInput("Enter ID of user you are sending to (0 to cancel): ");

        // Ask the user and capture the amount that they want to send
        BigDecimal amount = new BigDecimal(console.getUserInput("Enter amount: $"));

        // 3. Build the transfer object using your own id, the recpient's id, and the
        // amount
        Transfer transfer = new Transfer(2, 2, currentUser.getUser().getId() + 1000,
                Integer.parseInt(accountTo) + 1000, amount);
        System.out.println("Transfer Recipient: " + transfer.getAccountReceiver() + "       | Transfer Sender: "
                + transfer.getAccountSender() + "       | Amount: $" + transfer.getAmount());

        // 4. call sendBucks and pass in a transfer object.
        ts.sendBucks(transfer);
    }


    private void requestBucks() {
        // TODO Auto-generated method stub

    }

    private void exitProgram() {
        System.exit(0);
    }

    private void registerAndLogin() {
        while (!isAuthenticated()) {
            String choice = (String) console.getChoiceFromOptions(LOGIN_MENU_OPTIONS);
            if (LOGIN_MENU_OPTION_LOGIN.equals(choice)) {
                login();
            } else if (LOGIN_MENU_OPTION_REGISTER.equals(choice)) {
                register();
            } else {
                // the only other option on the login menu is to exit
                exitProgram();
            }
        }
    }

    private boolean isAuthenticated() {
        return currentUser != null;
    }

    private void register() {
        System.out.println("Please register a new user account");
        boolean isRegistered = false;
        while (!isRegistered) //will keep looping until user is registered
        {
            UserCredentials credentials = collectUserCredentials();
            try {
                authenticationService.register(credentials);
                isRegistered = true;
                System.out.println("Registration successful. You can now login.");
            } catch (AuthenticationServiceException e) {
                System.out.println("REGISTRATION ERROR: " + e.getMessage());
                System.out.println("Please attempt to register again.");
            }
        }
    }

    private void login() {
        System.out.println("Please log in");
        currentUser = null;
        while (currentUser == null) //will keep looping until user is logged in
        {
            UserCredentials credentials = collectUserCredentials();
            try {
                currentUser = authenticationService.login(credentials);
                this.ts = new TransferService(API_BASE_URL, currentUser);
            } catch (AuthenticationServiceException e) {
                System.out.println("LOGIN ERROR: " + e.getMessage());
                System.out.println("Please attempt to login again.");
            }
        }
    }

    private UserCredentials collectUserCredentials() {
        String username = console.getUserInput("Username");
        String password = console.getUserInput("Password");
        return new UserCredentials(username, password);
    }
}
