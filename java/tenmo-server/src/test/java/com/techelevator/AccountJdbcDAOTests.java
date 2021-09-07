package com.techelevator;

import com.techelevator.tenmo.dao.AccountJdbcDAO;
import com.techelevator.tenmo.model.Balance;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class AccountJdbcDAOTests extends TenmoDaoTests{

    BigDecimal bigDecimal = new BigDecimal("100.00");
    private Balance account1 = new Balance(bigDecimal.setScale(2), 2001,1001);

private Balance testBalance;
private AccountJdbcDAO sut;


@Before
    public void setup(){
    sut = new AccountJdbcDAO(dataSource);
    testBalance = new Balance(bigDecimal.setScale(2), 2001, 1001);

}


@Test
    public void getBalance_returns_correct_balance_for_user_id(){
    BigDecimal actual = sut.getBalance(100L).getBalance();
    BigDecimal expected = new BigDecimal("500");
    Assert.assertEquals(actual.compareTo(expected), 1);

}

@Test
public void getBalance_returns_correct_balance_for_account_id(){
    BigDecimal actual = sut.findAccountById(2001).getBalance();
    BigDecimal expected = new BigDecimal(100);
    Assert.assertEquals(actual.compareTo(expected), 1);


}

@Test
public void updated_balance_has_expected_values(){
    Balance balanceToUpdate = sut.getBalance(1001L);
//    BigDecimal expected  = new BigDecimal("100.00");
    balanceToUpdate.setBalance(bigDecimal);

    sut.updateBalance(balanceToUpdate);

    long accountToFind = balanceToUpdate.getAccountId();

    Balance retrieveBalance = sut.getBalance(1001L);
    assertBalancesMatch(balanceToUpdate, retrieveBalance);
}


private void assertBalancesMatch(Balance expected, Balance actual){
    Assert.assertEquals(expected.getAccountId(), actual.getAccountId());
    Assert.assertEquals(expected.getBalance(), actual.getBalance());
    Assert.assertEquals(expected.getUserId(), actual.getUserId());
}


}

