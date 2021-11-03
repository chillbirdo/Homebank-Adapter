package com.gf.doughflow.translator.model;

import com.gf.doughflow.translator.importer.BaseCsvImporter;

public class Account {

    private final int id;
    private final String name;
    private final Currency currency;
    private String importDir = null;
    private String importDirProcessed = null;
    private BaseCsvImporter importer;

    public Account(int id, String name, Currency currency, BaseCsvImporter importer){
        this.id = id;
        this.name = name;
        this.currency = currency;
        this.importer = importer;
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

    public String getImportDir() {
        return importDir;
    }

    public void setImportDir(String importDir) {
        this.importDir = importDir;
    }

    public BaseCsvImporter getImporter() {
        return importer;
    }

    public void setImporter(BaseCsvImporter importer) {
        this.importer = importer;
    }

    public String getImportDirProcessed() {
        return importDirProcessed;
    }

    public void setImportDirProcessed(String importDirProcessed) {
        this.importDirProcessed = importDirProcessed;
    }
}
