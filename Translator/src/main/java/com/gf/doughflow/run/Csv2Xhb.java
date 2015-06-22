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

        final String EBFILENAME = "path/to/easybankfile.csv";
        final String XHBFILENAME = "path/to/actual.xhb";
        final String OUTPUTFILENAME = "path/to/output.xhb";

        Map<Integer, Account> accounts = new TreeMap<Integer, Account>();
        accounts.put(1, new Account(1, "easy giro", Currency.EUR, new EasyBankImporter(1)));
        accounts.put(2, new Account(2, "easy spar", Currency.EUR, new EasyBankImporter(2)));

        File ebInputfile = new File(EBFILENAME);
        List<Transaction> ebTransactions = FileImporter.importRecordPerLine(accounts.get(1).getImporter(), ebInputfile);

        File xhbInputfile = new File(XHBFILENAME);
        XhbImporter xhbImporter = new XhbImporter();
        List<Transaction> xhbTransactions = FileImporter.importRecordPerLine(xhbImporter, xhbInputfile);

        Map<String, Transaction> xhbTransactionMap = new HashMap<String, Transaction>();
        for (Transaction t : xhbTransactions) {
            if (t != null) {
                xhbTransactionMap.put(extractKey(t), t);
            }
        }

        int d = 0;
        List<Transaction> nonDuplicates = new ArrayList<Transaction>();
        for (Transaction ebt : ebTransactions) {
            if (xhbTransactionMap.get(extractKey(ebt)) != null) {
                d++;
                continue;
            }
            nonDuplicates.add(ebt);
        }
        XhbInsertingExporter exporter = new XhbInsertingExporter(new File(XHBFILENAME));
        FileCreator fc = new FileCreator(exporter, nonDuplicates);
        fc.exportFile(new File(OUTPUTFILENAME));
    }

    private static String extractKey(Transaction t) {
        return t.getAccount().getId() + JulianDate.dateToJulian(t.getDate()) + t.getDescription();
    }
}
