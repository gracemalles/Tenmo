package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Balance;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
@Component
public class TransferJdbcDao implements TransferDao{

private JdbcTemplate jdbcTemplate;

public TransferJdbcDao(DataSource ds){
    this.jdbcTemplate = new JdbcTemplate(ds);
}
    // list to track all transfers
    private List<Transfer> transferList = new ArrayList<>();



    @Override
    public Transfer createNewTransfer(Transfer transfer) {
        String sql = "INSERT INTO transfers (transfer_type_id, " +
                "transfer_status_id, account_from, account_to, amount) VALUES(?, ?, ?, ?, ?) RETURNING transfer_id;";

        int id = jdbcTemplate.queryForObject(sql, Integer.class, transfer.getTransferTypeId(),
                transfer.getTransferStatusId(), transfer.getAccountSender(), transfer.getAccountReceiver()
                , transfer.getAmount());

        transfer.setTransferId(id);

        return transfer;
    }
    // 5. view transfers i have sent / received

    @Override
    public List<Transfer> getAllTransfers(int userId) {
        List<Transfer> transfers = new ArrayList<>();
        String sql2 = "SELECT transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount " +
       "FROM transfers " +
        "JOIN accounts ON accounts.account_id = transfers.account_from OR accounts.account_id = transfers.account_to " +
        "WHERE user_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql2, userId);
        while(results.next()){
            Transfer transfer1 = new Transfer();
            transfer1.setTransferId(results.getInt("transfer_id"));
            transfer1.setTransferTypeId(results.getInt("transfer_type_id"));
            transfer1.setTransferStatusId(results.getInt("transfer_status_id"));
            transfer1.setAccountSender(results.getInt("account_from"));
            transfer1.setAccountReceiver(results.getInt("account_to"));
            transfer1.setAmount(results.getBigDecimal("amount"));
            transfers.add(transfer1);
        }
        return transfers;

    }
    // 6.
    @Override
    public List<Transfer> getTransfersByTransferId(int transferId) {
        List<Transfer> transfers = new ArrayList<>();
        String sql3 = "SELECT transfer_id,transfer_type_id, transfer_status_id, account_from, account_to, amount FROM transfers " +
                "WHERE transfer_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql3, transferId);
        while(results.next()){
            Transfer transfer1 = new Transfer();
            transfer1.setTransferId(results.getInt("transfer_id"));
            transfer1.setTransferTypeId(results.getInt("transfer_type_id"));
            transfer1.setTransferStatusId(results.getInt("transfer_status_id"));
            transfer1.setAccountSender(results.getInt("account_from"));
            transfer1.setAccountReceiver(results.getInt("account_to"));
            transfer1.setAmount(results.getBigDecimal("amount"));
            transfers.add(transfer1);
        }
        return transfers;

    }


}
