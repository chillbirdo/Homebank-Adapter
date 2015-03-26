package com.gf.doughflow.run;

import com.gf.doughflow.translator.exporter.FileExporter;
import com.gf.doughflow.translator.exporter.QifExporter;
import com.gf.doughflow.translator.importer.EasyBankImporter;
import com.gf.doughflow.translator.importer.FileImporter;
import com.gf.doughflow.translator.model.Account;
import com.gf.doughflow.translator.model.Currency;
import com.gf.doughflow.translator.model.Transaction;
import java.io.File;
import java.util.List;

public class Csv2Qif {

    
    public static void main(String a[]){
        
//        final String INPUTFILENAME = "/home/gilbert/temp/giro.csv";
//        final String OUTPUTFILENAME = "/home/gilbert/temp/giro.qif";
        final String INPUTFILENAME = "/home/gilbert/temp/sparkonto.csv";
        final String OUTPUTFILENAME = "/home/gilbert/temp/sparkonto.qif";
//        final String INPUTFILENAME = "/home/gilbert/temp/kreditkarte.csv";
//        final String OUTPUTFILENAME = "/home/gilbert/temp/kreditkarte.qif";
        
        File inputfile = new File(INPUTFILENAME);
        Account account = new Account(1, "easybank-giro", Currency.EUR);
        EasyBankImporter ebi = new EasyBankImporter(account);
        List<Transaction> transactions = FileImporter.importRecordPerLine(ebi, inputfile);
        
        FileExporter.exportFile(transactions, new QifExporter(), OUTPUTFILENAME);
    }
}
