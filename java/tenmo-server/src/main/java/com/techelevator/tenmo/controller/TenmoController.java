package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDAO;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Balance;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")

public class TenmoController {

    @Autowired
    AccountDAO dao;
    @Autowired
    UserDao userDao;
    @Autowired
    TransferDao transferDao;


    @RequestMapping(path = "/balance", method = RequestMethod.GET)
    public BigDecimal getBalance(Principal principal){
        Long id = getUserId(principal);
        return dao.getBalance(id).getBalance();
    }

    public Long getUserId(Principal principal){
        return userDao.findByUsername(principal.getName()).getId();
    }

    @RequestMapping(path = "/all", method = RequestMethod.GET)
    public List<User> getAll(Principal principal){

        return userDao.findAll();
    }
    // 4.
    //request mapping to accept the post
    // sending transfer object to TransferJdbcDAO
    @RequestMapping(path = "/transfers", method = RequestMethod.POST)
    public Transfer createNewTransfer(@RequestBody Transfer transfer){
        Transfer request = transferDao.createNewTransfer(transfer);
        // calling below method and passing in information from request
        updatedBalance(request);
        return request;
    }
    // 4.
    // take request and turn into sep accounts
    public void updatedBalance(Transfer request){
        // create 2 balance objects
        Balance accountTo = dao.findAccountById(request.getAccountReceiver());
        Balance accountFrom = dao.findAccountById(request.getAccountSender());
        // transfer() updates account balances and makes sure sender has enough $ to make
        // transfer
        accountFrom.transfer(accountTo, request.getAmount());
        dao.updateBalance(accountTo);
        dao.updateBalance(accountFrom);

    }
    // 5.
    @RequestMapping(path = "/displayTransfers", method = RequestMethod.GET)
    public List<Transfer> getAllTransfers(Principal principal){
        List<Transfer> transferList = transferDao.getAllTransfers(userDao.findIdByUsername(principal.getName()));
        return transferList;

    }
    // 6.
    @RequestMapping(path = "/transfers/{id}", method = RequestMethod.GET)
    public List<Transfer> getTransfersByTransferId(@PathVariable int transferId){
        return transferDao.getTransfersByTransferId(transferId);
    }



    }






