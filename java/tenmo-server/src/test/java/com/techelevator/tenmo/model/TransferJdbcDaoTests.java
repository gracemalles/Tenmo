package com.techelevator.tenmo.model;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.techelevator.TenmoDaoTests;
import com.techelevator.tenmo.dao.TransferJdbcDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

public class TransferJdbcDaoTests extends TenmoDaoTests {

    BigDecimal bigDecimal = new BigDecimal(300);
    private Transfer transfer1 = new Transfer(1,
            1, 2001, 2002, bigDecimal.setScale(2));

    private Transfer testTransfer;
    private TransferJdbcDao sut;

    @Before
    public void setup(){
        sut = new TransferJdbcDao(dataSource);
        testTransfer = new Transfer(1,
                1, 2001, 2002, bigDecimal.setScale(2));
    }

    @Test
    public void new_transfer_has_expected_values(){
    Transfer createdTransfer = sut.createNewTransfer(testTransfer);

    int newTransferId = createdTransfer.getTransferId();
    Assert.assertTrue(newTransferId > 0);

    testTransfer.setTransferId(newTransferId);
    assertTransfersMatch(testTransfer, createdTransfer);

    }

    @Test
    public void get_all_transfers_by_user_id(){
        transfer1.setTransferId(4001);
        List<Transfer> transferList = sut.getAllTransfers(1001);
        Assert.assertEquals(2, transferList.size());
        assertTransfersMatch(transfer1, transferList.get(0));
    }


    @Test
    public void getTransfer_returns_correct_transfers_for_transfer_id(){
        transfer1.setTransferId(4001);
        List<Transfer> transfer = sut.getTransfersByTransferId(4001);
        Assert.assertEquals(1, transfer.size());
        assertTransfersMatch(transfer1, transfer.get(0));
//          List<Transfer> transfer = sut.getTransfersByTransferId(3001);
//          Assert.assertNotNull(transfer);
//          assertTransfersMatch(transfer1, transfer);

    }


    private void assertTransfersMatch(Transfer expected, Transfer actual){
        Assert.assertEquals(expected.getTransferId(), actual.getTransferId());
        Assert.assertEquals(expected.getTransferTypeId(), actual.getTransferTypeId());
        Assert.assertEquals(expected.getTransferStatusId(), actual.getTransferStatusId());
        Assert.assertEquals(expected.getAccountSender(), actual.getAccountSender());
        Assert.assertEquals(expected.getAccountReceiver(), actual.getAccountReceiver());
        Assert.assertEquals(expected.getAmount(), actual.getAmount());



    }

}
