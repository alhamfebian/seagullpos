package com.blibli.seagullpos.model;

import java.util.Date;
import java.util.List;

public class HeaderTransactionModel {
    private int transactionId;
    private MemberModel member;
    private EmployeeModel employee;
    private Date transactionDate;
    private List<DetailTransactionModel> detailTransaction;

    public List<DetailTransactionModel> getDetailTransaction() {
        return detailTransaction;
    }

    public void setDetailTransaction(List<DetailTransactionModel> detailTransaction) {
        this.detailTransaction = detailTransaction;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public MemberModel getMember() {
        return member;
    }

    public void setMember(MemberModel member) {
        this.member = member;
    }

    public EmployeeModel getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeModel employee) {
        this.employee = employee;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }
}
