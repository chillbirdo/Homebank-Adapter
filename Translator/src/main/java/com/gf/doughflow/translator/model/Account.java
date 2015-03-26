package com.gf.doughflow.translator.model;

public class Account {

    private final int id;
    private final String name;
    private final Currency currency;

    public Account(int id, String name, Currency currency){
        this.id = id;
        this.name = name;
        this.currency = currency;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Currency getCurrency() {
        return currency;
    }
}
