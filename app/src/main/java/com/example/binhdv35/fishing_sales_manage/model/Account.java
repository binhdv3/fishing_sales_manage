package com.example.binhdv35.fishing_sales_manage.model;

import java.util.ArrayList;
import java.util.List;

public class Account {

    public static List<Account> accountList = new ArrayList<>();

    private String id, accountName, passWord, idCustomer;

    public Account(String id, String accountName, String passWord, String idCustomer) {
        this.id = id;
        this.accountName = accountName;
        this.passWord = passWord;
        this.idCustomer = idCustomer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String userName) {
        this.accountName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(String idCustomer) {
        this.idCustomer = idCustomer;
    }
}
