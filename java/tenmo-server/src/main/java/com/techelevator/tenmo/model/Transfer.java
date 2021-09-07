package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Transfer {

    private Integer transferId;
    private Integer transferTypeId;
    private Integer transferStatusId;
    private Integer accountSender;
    private Integer accountReceiver;
    private BigDecimal amount;

    public Transfer(Integer transferTypeId, Integer transferStatusId,
                    Integer accountSender, Integer accountReceiver, BigDecimal amount) {
        this.transferTypeId = transferTypeId;
        this.transferStatusId = transferStatusId;
        this.accountSender = accountSender;
        this.accountReceiver = accountReceiver;
        this.amount = amount;
    }

    public Transfer() {

    }

    public Integer getTransferId() {
        return transferId;
    }

    public void setTransferId(Integer transferId) {
        this.transferId = transferId;
    }

    public Integer getTransferTypeId() {
        return transferTypeId;
    }

    public void setTransferTypeId(Integer transferTypeId) {
        this.transferTypeId = transferTypeId;
    }

    public Integer getTransferStatusId() {
        return transferStatusId;
    }

    public void setTransferStatusId(Integer transferStatusId) {
        this.transferStatusId = transferStatusId;
    }

    public Integer getAccountSender() {
        return accountSender;
    }

    public void setAccountSender(Integer accountSender) {
        this.accountSender = accountSender;
    }

    public Integer getAccountReceiver() {
        return accountReceiver;
    }

    public void setAccountReceiver(Integer accountReceiver) {
        this.accountReceiver = accountReceiver;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }



}
