package com.gf.doughflow.translator.importer;

import com.gf.doughflow.translator.model.Account;
import com.gf.doughflow.translator.model.Transaction;
import com.gf.doughflow.translator.util.AmountConverter;
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
        Account account = AccountRegistry.get(accountIdInWorkspace);
        Transaction transaction = new Transaction();
        transaction.setDate(convertDate(record[csvImporterProperties.getDateColumn()]));
        if(account.getIgnoreBefore() != null && transaction.getDate().before(account.getIgnoreBefore())){
            return null;
        }
        transaction.setAccount(account);
        transaction.setDescription(convertDescription(record));
        transaction.setValue(convertAmount(record[csvImporterProperties.getAmountColumn()]));
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
        return AmountConverter.convertAmount(amountStr, csvImporterProperties.getFloatingPointCharacter());
    }

    /**
     * a description may consists of multiple concatenated columns
     * @param record
     * @return
     */
    protected String convertDescription(String[] record) {
        String description = new String();
        boolean descriptionYetEmpty = true;
        for (Integer descriptionCol : csvImporterProperties.getDescriptionColumns()) {
            if(record[descriptionCol].trim().isEmpty()){
                continue;
            }
            if (!descriptionYetEmpty) {
                description = description.concat(" - ");
            }
            description = description.concat(record[descriptionCol]);
            descriptionYetEmpty = false;
        }
        return DescriptionConverter.replaceGermanLetters(DescriptionConverter.removeSpaces(DescriptionConverter.removeQuotes(description)));
    }


    public CsvImporterProperties getCsvImporterProperties() {
        return csvImporterProperties;
    }

}
