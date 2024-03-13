package be.uantwerpen.fti.ei.rest;

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
