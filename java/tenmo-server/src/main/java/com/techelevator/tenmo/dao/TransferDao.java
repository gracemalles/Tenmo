package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.util.List;

public interface TransferDao {

    Transfer createNewTransfer(Transfer transfer);

     List<Transfer> getAllTransfers(int userId);

     List<Transfer> getTransfersByTransferId(int transferId);

}
