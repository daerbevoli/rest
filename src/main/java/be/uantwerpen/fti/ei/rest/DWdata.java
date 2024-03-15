package be.uantwerpen.fti.ei.rest;

/**
 * The DWdata (Deposit/Withdraw) class exists for the purpose of fetching the account associated
 * with the hashcode to take actions on (like deposit and withdraw money)
 * */
public class DWdata {
    private int hash;
    private int amount;

    public DWdata(int hash, int amount){
        this.hash = hash;
        this.amount = amount;
    }

    public int getHash() {
        return hash;
    }

    public int getAmount() {
        return amount;
    }
}
