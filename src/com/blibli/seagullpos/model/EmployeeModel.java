package com.blibli.seagullpos.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EmployeeModel {

    private String employeeId;
    private String employeeName;
    private String employeeEmail;
    private String employeeGender;
    private String employeePassword;
    private String employeeRole;
    private Date lastLogin;
    private SummaryList<HeaderTransactionModel> listEmployeeTransaction;

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public String getEmployeeGender() {
        return employeeGender;
    }

    public void setEmployeeGender(String employeeGender) {
        this.employeeGender = employeeGender;
    }

    public String getEmployeePassword() {
        return employeePassword;
    }

    public void setEmployeePassword(String employeePassword) {
        this.employeePassword = employeePassword;
    }

    public String getEmployeeRole() {
        return employeeRole;
    }

    public void setEmployeeRole(String employeeRole) {
        this.employeeRole = employeeRole;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getFormattedDate(){
        return new SimpleDateFormat("E dd-MM-yyyy hh:mm:ss").format(getLastLogin());
    }

    public SummaryList<HeaderTransactionModel> getListEmployeeTransaction() {
        return listEmployeeTransaction;
    }

    public void setListEmployeeTransaction(SummaryList<HeaderTransactionModel> listEmployeeTransaction) {
        this.listEmployeeTransaction = listEmployeeTransaction;
    }
}
