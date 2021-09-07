package com.techelevator.tenmo.model;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class BalanceTest {

    @Test
    public void get_Correct_Balance(){
        Balance balance = new Balance();
        BigDecimal expected = BigDecimal.valueOf(1000);
        Assert.assertEquals(expected, balance.getBalance());
    }

    @Test
    public void test_Set_And_Get_Correct_Balance(){
        BigDecimal a = BigDecimal.valueOf(150);
        Balance balance = new Balance();
        balance.setBalance(BigDecimal.valueOf(150));
        BigDecimal balance2 = balance.getBalance();
        Assert.assertEquals(balance2, BigDecimal.valueOf(150));

    }

}