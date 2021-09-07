package com.techelevator.tenmo.services;

import com.techelevator.tenmo.App;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.view.ConsoleService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

public class TransferService {


    private  String baseUrl;
    private RestTemplate restTemplate = new RestTemplate();
    private AuthenticatedUser currentUser;

    public TransferService(String baseUrl, AuthenticatedUser currentUser) {
        this.baseUrl = baseUrl;
        this.currentUser = currentUser;
        this.restTemplate = new RestTemplate();
    }

    public void viewCurrentBalance(){

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setBearerAuth(currentUser.getToken());
        HttpEntity entity = new HttpEntity(httpHeaders);

        BigDecimal balance = restTemplate.exchange("http://localhost:8080/balance",
                HttpMethod.GET, entity,
                BigDecimal.class).getBody();

        System.out.println("Your current balance is: $" + balance);
    }

    public User[] retrieveAllUsersForDisplay() {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setBearerAuth(currentUser.getToken());
        HttpEntity entity = new HttpEntity(httpHeaders);
        User[] users = null;
        users = restTemplate.exchange("http://localhost:8080/all",
                HttpMethod.GET, entity, User[].class).getBody();

        return users;

    }

    public Transfer sendBucks(Transfer transfer) {
        // TODO Auto-generated method stub
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setBearerAuth(currentUser.getToken());
        HttpEntity entity = new HttpEntity(transfer, httpHeaders);

        transfer = restTemplate.exchange("http://localhost:8080/transfers",
                HttpMethod.POST, entity,
                Transfer.class).getBody();
        return transfer;
    }
    //5
    public Transfer[] viewTransferHistory(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setBearerAuth(currentUser.getToken());
        HttpEntity entity = new HttpEntity(httpHeaders);
        Transfer[] transfers = null;
        transfers = restTemplate.exchange("http://localhost:8080/displayTransfers",
                HttpMethod.GET, entity,
                Transfer[].class).getBody();

        return transfers;

    }
    //6
    public Transfer[] viewTransferHistoryByTransferId(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setBearerAuth(currentUser.getToken());
        HttpEntity entity = new HttpEntity(httpHeaders);
        Transfer[] transfers = null;
        transfers = restTemplate.exchange("http://localhost:8080/displayTransfersByTransferId",
                HttpMethod.GET, entity,
                Transfer[].class).getBody();

        return transfers;


    }


}
