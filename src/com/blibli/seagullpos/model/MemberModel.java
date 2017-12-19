package com.blibli.seagullpos.model;

import java.util.Date;

public class MemberModel {
    private long memberId;
    private String memberName;
    private int memberPoint;
    private String memberEmail;
    private String phoneNumber;
    private Date registerDate;
    private int totalTransaction;
    private SummaryList<HeaderTransactionModel> listMemberTransaction;

    public long getMemberID() {
        return memberId;
    }

    public void setMemberID(long memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public int getMemberPoint() {
        return memberPoint;
    }

    public void setMemberPoint(int memberPoint) {
        this.memberPoint = memberPoint;
    }

    public String getMemberEmail() {
        return memberEmail;
    }

    public void setMemberEmail(String memberEmail) {
        this.memberEmail = memberEmail;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public SummaryList<HeaderTransactionModel> getListMemberTransaction() {
        return listMemberTransaction;
    }

    public void setListMemberTransaction(SummaryList<HeaderTransactionModel> listMemberTransaction) {
        this.listMemberTransaction = listMemberTransaction;
    }

    public int getTotalTransaction() {
        return totalTransaction;
    }

    public void setTotalTransaction(int totalTransaction) {
        this.totalTransaction = totalTransaction;
    }
}
