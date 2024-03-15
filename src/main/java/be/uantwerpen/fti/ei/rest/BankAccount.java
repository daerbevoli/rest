package be.uantwerpen.fti.ei.rest;

/**
 * The BankAccount class has properties name, balance and accountnumber.
 * There are getter and setter for each property.
 * The object had a deposit and withdraw method.
 * It also had a getHash method for the accountnumber that return a unique hashnumber associated with the account
 */
public class BankAccount {
    private String name;
    private double balance;
    private String accountNumber;

    public BankAccount(String name, double balance, String accountNumber){
        this.name = name;
        this.balance = balance;
        this.accountNumber = accountNumber;
    }

    public String getName() {
        return name;
    }


    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getAccountNumber(){
        return accountNumber;
    }

    public void deposit(double amount){
        this.balance += amount;
    }

    public void withdraw(double amount){
        this.balance -= amount;
    }

    public int getHash(){
        return accountNumber.hashCode();
    }

}
