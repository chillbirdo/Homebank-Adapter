package com.gf.doughflow.translator.importer;

import java.text.SimpleDateFormat;
import java.util.logging.Logger;
import com.gf.doughflow.translator.model.Account;
import com.gf.doughflow.translator.model.Transaction;
import java.text.DecimalFormat;

public class EasyBankImporter implements IImporter {

    private final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
    private final Account account;
    
    public EasyBankImporter(Account account){
        this.account = account;
    }
    
    public Transaction toTransaction(Object record) throws Exception{
        String[] recordSplit = ((String)record).split(";");
        if(recordSplit.length != 6){
            throw new Exception("Invalid record: " + record);
        }
        Transaction t = new Transaction();
        t.setAccount(this.account);
        t.setDescription(convertDescription(recordSplit[1]));
        t.setValue(convertToDouble(recordSplit[4]));
        t.setDate(sdf.parse(recordSplit[3]));
        return t;
    }
    
    private String convertDescription(String description){
        String ret = description;
        while( ret.contains("  ")){
            ret = ret.replaceAll("  ", " ");
        }
        return replaceGermanLetters(ret);
    }
    
    private String replaceGermanLetters(String description) {
        return description.toLowerCase().replaceAll("ä", "ae").replaceAll("ö", "oe").replaceAll("ü", "ue").replaceAll("ß", "ss");
    }
    
    private Double convertToDouble(String ds){
        String dsPeriod = ds.replaceAll("\\.", "").replaceAll(",", ".");
        return Double.valueOf(dsPeriod);
    }
}
