package com.gf.doughflow.run;

import com.gf.doughflow.translator.exporter.FileCreator;
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
        final String INPUTFILENAME = "/home/gilbert/Desktop/EASYBANK_Umsatzliste_20150403_2148.csv";
        final String OUTPUTFILENAME = "/home/gilbert/Desktop/easy.qif";
//        final String INPUTFILENAME = "/home/gilbert/temp/kreditkarte.csv";
//        final String OUTPUTFILENAME = "/home/gilbert/temp/kreditkarte.qif";
        
        File inputfile = new File(INPUTFILENAME);
        Account account = new Account(1, "easybank-giro", Currency.EUR, new EasyBankImporter(1));
        List<Transaction> transactions = FileImporter.importRecordPerLine(account.getImporter(), inputfile);
        
        FileCreator fc = new FileCreator(new QifExporter(), transactions);
        fc.exportFile(new File(OUTPUTFILENAME));
    }
}
