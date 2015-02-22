package com.gf.doughflow.translator.run;

import com.gf.doughflow.translator.exporter.FileExporter;
import com.gf.doughflow.translator.exporter.QifExporter;
import com.gf.doughflow.translator.importer.EasyBankImporter;
import com.gf.doughflow.translator.importer.FileImporter;
import com.gf.doughflow.translator.model.Account;
import com.gf.doughflow.translator.model.Currency;
import com.gf.doughflow.translator.model.Transaction;
import java.io.File;
import java.util.List;

public class Strandalone {

    
    public static void main(String a[]){
        
        final String INPUTFILENAME = "/easybank.csv";
        final String OUTPUTFILENAME = "/output.qif";
        
        File inputfile = new File(INPUTFILENAME);
        Account account = new Account(1, "easybank-giro", Currency.EUR);
        EasyBankImporter ebi = new EasyBankImporter(account);
        List<Transaction> transactions = FileImporter.importRecordPerLine(ebi, inputfile);
        
        FileExporter.exportFile(transactions, new QifExporter(), OUTPUTFILENAME);
    }
}
