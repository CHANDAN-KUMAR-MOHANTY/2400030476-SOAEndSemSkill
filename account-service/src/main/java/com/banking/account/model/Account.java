package com.banking.account.model;

public class Account {
    private String accountId;
    private String holderName;
    private String accountType;
    private double balance;

    public Account() {}

    public Account(String accountId, String holderName, String accountType, double balance) {
        this.accountId = accountId;
        this.holderName = holderName;
        this.accountType = accountType;
        this.balance = balance;
    }

    public String getAccountId() { return accountId; }
    public void setAccountId(String accountId) { this.accountId = accountId; }

    public String getHolderName() { return holderName; }
    public void setHolderName(String holderName) { this.holderName = holderName; }

    public String getAccountType() { return accountType; }
    public void setAccountType(String accountType) { this.accountType = accountType; }

    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }
}
