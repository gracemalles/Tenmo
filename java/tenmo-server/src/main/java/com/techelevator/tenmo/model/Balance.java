package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Balance {

   private BigDecimal balance;
   private int accountId;
   private int userId;

    public Balance() {
        this.balance = BigDecimal.valueOf(1000);
    }

    public Balance(BigDecimal balance, int accountId, int userId) {
        this.balance = balance;
        this.accountId = accountId;
        this.userId = userId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    //method that gives all balance objects ability to transfer money
    // (take in balance and big decimal for money)
    public void transfer(Balance balanceTo, BigDecimal amountToTransfer){
        if (balance.compareTo(amountToTransfer) >= 0){
            balance = balance.subtract(amountToTransfer);
            // balanceTo --> account you are sending $ to
            balanceTo.balance = balanceTo.balance.add(amountToTransfer);
        } else{
            System.out.println("Insufficient funds");
        }
    }
}
