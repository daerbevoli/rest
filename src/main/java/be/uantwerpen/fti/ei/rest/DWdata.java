package be.uantwerpen.fti.ei.rest;

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
