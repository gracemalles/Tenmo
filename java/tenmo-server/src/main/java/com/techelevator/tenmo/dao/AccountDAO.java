package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Balance;

import java.math.BigDecimal;

public interface AccountDAO {
    Balance getBalance(Long user);

    void updateBalance(Balance balance);

    Balance findAccountById(int id);


}
