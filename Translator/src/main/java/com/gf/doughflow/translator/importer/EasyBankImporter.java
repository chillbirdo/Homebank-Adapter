package com.gf.doughflow.translator.importer;

import java.text.SimpleDateFormat;
import java.util.logging.Logger;
import com.gf.doughflow.translator.model.Account;
import com.gf.doughflow.translator.model.Transaction;

public class EasyBankImporter implements IImporter {

    private final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    
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
        t.setDescription(recordSplit[1]);
        t.setValue(convertToDouble(recordSplit[4]));
        t.setDate(sdf.parse(recordSplit[3]));
        return t;
    }
    
    private Double convertToDouble(String ds){
        String dsPeriod = ds.replaceAll("\\.", "").replaceAll(",", ".");
        return Double.valueOf(dsPeriod);
    }
}
