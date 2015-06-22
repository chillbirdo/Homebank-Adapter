package com.gf.doughflow.translator.model;

import com.gf.doughflow.translator.importer.IImporter;

public class Account {

    private final int id;
    private final String name;
    private final Currency currency;
    private String importDir = null;
    private String importDirProcessed = null;
    private IImporter importer;
    
    public Account(int id, String name, Currency currency, IImporter importer){
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

    public IImporter getImporter() {
        return importer;
    }

    public void setImporter(IImporter importer) {
        this.importer = importer;
    }

    public String getImportDirProcessed() {
        return importDirProcessed;
    }

    public void setImportDirProcessed(String importDirProcessed) {
        this.importDirProcessed = importDirProcessed;
    }
}
