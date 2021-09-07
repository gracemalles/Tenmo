package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Balance;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;

@Component
public class AccountJdbcDAO implements AccountDAO{

    private JdbcTemplate jdbcTemplate;

    public AccountJdbcDAO(DataSource ds){
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    @Override
    public Balance getBalance(Long userId) {
        String sql = "SELECT user_id, account_id, balance FROM accounts WHERE user_id = ?";
        Balance balance = new Balance();
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);

        if (results.next()){

            BigDecimal balance1 = results.getBigDecimal("balance");
            int userId1 = results.getInt("user_id");
            int accountId1 = results.getInt("account_id");

            balance.setBalance(balance1);
            balance.setUserId(userId1);
            balance.setAccountId(accountId1);

        }

        return balance;
    }
    // update balance --> take in Balance object for specific account
    // invoke everytime a transfer is made
    // in order update from/ to account balance
    //One method updateBalance
    @Override
    public void updateBalance(Balance balance){
        String sql = "UPDATE accounts SET balance = ? WHERE account_id = ?";
        jdbcTemplate.update(sql, balance.getBalance(), balance.getAccountId());
    }

    public Balance findAccountById(int id){
        Balance balance = new Balance();
        String sql = "SELECT * FROM accounts WHERE account_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
        if (results.next()){
            balance.setBalance(results.getBigDecimal("balance"));
            balance.setUserId(results.getInt("user_id"));
            balance.setAccountId(results.getInt("account_id"));
        }
        return balance;
    }

}
