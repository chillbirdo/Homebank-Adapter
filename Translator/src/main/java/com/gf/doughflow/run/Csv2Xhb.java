package com.gf.doughflow.run;

import com.gf.doughflow.translator.exporter.FileCreator;
import com.gf.doughflow.translator.exporter.XhbInsertingExporter;
import com.gf.doughflow.translator.importer.EasyBankImporter;
import com.gf.doughflow.translator.importer.FileImporter;
import com.gf.doughflow.translator.importer.XhbImporter;
import com.gf.doughflow.translator.model.Account;
import com.gf.doughflow.translator.model.Currency;
import com.gf.doughflow.translator.model.Transaction;
import com.gf.doughflow.util.JulianDate;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Csv2Xhb {

    public static void main(String a[]) {

        final String EBFILENAME =  "/home/gilbert/homebank/copy/import/giro/EASYBANK_Umsatzliste_20150531_1150.csv";
        final String XHBFILENAME = "/home/gilbert/homebank/copy/actual/actual.xhb";
        final String OUTPUTFILENAME = "/home/gilbert/homebank/copy/actual/output.xhb";

        Map<String, Account> accounts = new TreeMap<String, Account>();
        accounts.put("1", new Account(1, "easy giro", Currency.EUR));
        accounts.put("2", new Account(2, "easy spar", Currency.EUR));

        File ebInputfile = new File(EBFILENAME);
        EasyBankImporter ebImporter = new EasyBankImporter(accounts.get("1"));
        List<Transaction> ebTransactions = FileImporter.importRecordPerLine(ebImporter, ebInputfile);
        
        File xhbInputfile = new File(XHBFILENAME);
        XhbImporter xhbImporter = new XhbImporter(accounts);
        List<Transaction> xhbTransactions = FileImporter.importRecordPerLine(xhbImporter, xhbInputfile);
        
        Map<String, Transaction> xhbTransactionMap = new HashMap<String,Transaction>();
        for(Transaction t : xhbTransactions){
            if( t != null){
                xhbTransactionMap.put( extractKey(t), t);
            }
        }
        
        int d = 0;
        List<Transaction> nonDuplicates = new ArrayList<Transaction>();
        for( Transaction ebt : ebTransactions){
            if( xhbTransactionMap.get(extractKey(ebt)) != null){
                d++;
                continue;
            }
            nonDuplicates.add(ebt);
        }
        
        XhbInsertingExporter exporter = new XhbInsertingExporter(XHBFILENAME);
        FileCreator fc = new FileCreator(exporter, nonDuplicates);
        fc.exportFile(OUTPUTFILENAME);
        
        System.out.println("GIB DIR " + d);
    }
    
    private static String extractKey(Transaction t){
        return t.getAccount().getId() + JulianDate.dateToJulian(t.getDate()) + t.getDescription();
    }
}
