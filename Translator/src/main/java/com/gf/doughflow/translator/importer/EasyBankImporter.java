package com.gf.doughflow.translator.importer;

import java.text.SimpleDateFormat;
import com.gf.doughflow.translator.model.Transaction;
import com.gf.doughflow.workspace.AccountRegistry;

public class EasyBankImporter implements IImporter {

    private final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
    private final int accountId;

    public EasyBankImporter(int accountId) {
        this.accountId = accountId;
    }

    @Override
    public Transaction toTransaction(Object record) throws Exception {
        String[] recordSplit = ((String) record).split(";");
        if (recordSplit.length != 6) {
            throw new Exception("Invalid record: " + record);
        }
        Transaction t = new Transaction();
        t.setAccount(AccountRegistry.get(accountId));
        t.setDescription(convertDescription(recordSplit[1]));
        t.setValue(convertToDouble(recordSplit[4]));
        t.setDate(sdf.parse(recordSplit[3]));
        return t;
    }

    private String convertDescription(String description) {
        return replaceGermanLetters(removeSpaces(removeQuotes(description)));
    }

    private String removeQuotes(String input){
        return input.replaceAll("\"", "");
    }
    
    private String removeSpaces(String input) {
        String ret = input;
        while (ret.contains("  ")) {
            ret = ret.replaceAll("  ", " ");
        }
        return ret;
    }

    private String replaceGermanLetters(String description) {
        return description.toLowerCase().replaceAll("ä", "ae").replaceAll("ö", "oe").replaceAll("ü", "ue").replaceAll("ß", "ss");
    }

    private Double convertToDouble(String ds) {
        String dsPeriod = ds.replaceAll("\\.", "").replaceAll(",", ".");
        return Double.valueOf(dsPeriod);
    }
}
