package com.gf.doughflow.translator.importer;

import com.gf.doughflow.translator.model.Transaction;
import com.gf.doughflow.translator.util.DescriptionConverter;
import com.gf.doughflow.workspace.AccountRegistry;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseCsvImporter implements IImporter {

    protected Charset charset = StandardCharsets.UTF_8;
    protected CsvImporterProperties csvImporterProperties;
    protected int accountIdInWorkspace;

    public BaseCsvImporter(int accountIdInWorkspace) {
        this.accountIdInWorkspace = accountIdInWorkspace;
    }

    public Transaction toTransaction(String[] record) throws Exception {
        Transaction transaction = new Transaction();
        transaction.setAccount(AccountRegistry.get(accountIdInWorkspace));
        transaction.setDescription(convertDescription(record[csvImporterProperties.getDescriptionColumn()]));
        transaction.setValue(convertAmount(record[csvImporterProperties.getAmountColumn()]));
        transaction.setDate(convertDate(record[csvImporterProperties.getDateColumn()]));
        return transaction;
    }

    @Override
    public Charset getCharset() {
        return charset;
    }

    protected Date convertDate(String dateStr) throws ParseException {
        return new SimpleDateFormat(this.csvImporterProperties.getDateFormat()).parse(dateStr);
    }

    protected Double convertAmount(String amountStr) {
        return Double.valueOf(amountStr);
    }

    protected String convertDescription(String description) {
        return DescriptionConverter.replaceGermanLetters(DescriptionConverter.removeSpaces(DescriptionConverter.removeQuotes(description)));
    }


    public CsvImporterProperties getCsvImporterProperties() {
        return csvImporterProperties;
    }

}
