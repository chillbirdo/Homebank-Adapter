package com.gf.doughflow.translator.importer;

import com.gf.doughflow.translator.model.Transaction;

public interface IImporter {

    public Transaction toTransaction(Object record) throws Exception;
    
}
