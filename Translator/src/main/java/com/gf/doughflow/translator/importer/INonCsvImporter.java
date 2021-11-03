package com.gf.doughflow.translator.importer;

import com.gf.doughflow.translator.model.Transaction;

public interface INonCsvImporter extends IImporter {

    Transaction toTransaction(String line) throws Exception;
}
