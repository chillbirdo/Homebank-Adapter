package com.gf.doughflow.translator.exporter;

import com.gf.doughflow.translator.importer.FileReader;
import com.gf.doughflow.translator.importer.XhbImporter;
import com.gf.doughflow.translator.model.Transaction;
import com.gf.doughflow.translator.util.HomebankDate;
import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Inserts the collection of transactions of the superclass into xhbActualFile.
 * Therefore, it splits the file into contentBeforeInsertedTransactions, which contains the header and all the transactions from xhbActualFile,
 * and contentAfterInsertedTransactions, which contains the rest of the xhbActualFile.
 * In this way, new transactions are appended to the transactions in xhbActualFile.
 */
public class XhbInsertingExporter extends XhbExporter {

    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private final String BEFORE_LINE_PREFIX = "</homebank>";

    public File xhbActualFile;
    private String contentBeforeInsertedTransactions = null;
    private String contentAfterInsertedTransactions = null;

    public XhbInsertingExporter(File xhbActualFile) {
        super();
        this.xhbActualFile = xhbActualFile;
        initContentBeforeAfter();
    }

    public List<Transaction> filterDuplicates(List<Transaction> freshTransactions) {
        XhbImporter xhbImporter = new XhbImporter();
        List<Transaction> xhbTransactions = FileReader.importNonCsv(xhbImporter, xhbActualFile);

        Map<String, Transaction> xhbTransactionMap = new HashMap<>();
        for (Transaction t : xhbTransactions) {
            xhbTransactionMap.put(extractKey(t), t);
        }
        List<Transaction> nonDuplicates = new ArrayList<>();
        for (Transaction ebt : freshTransactions) {
            if (xhbTransactionMap.get(extractKey(ebt)) != null) {
                continue;
            }
            nonDuplicates.add(ebt);
        }
        return nonDuplicates;
    }

    private String extractKey(Transaction t) {
        return t.getAccount().getId() + "" + HomebankDate.dateToHomebankDate(t.getDate()) + t.getDescription();
    }

    @Override
    public String createHeader() {
        return contentBeforeInsertedTransactions;
    }

    @Override
    public String createTrailer() {
        return contentAfterInsertedTransactions;
    }

    public void initContentBeforeAfter() {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new java.io.FileReader(xhbActualFile));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                if (line.startsWith(BEFORE_LINE_PREFIX)) {
                    contentBeforeInsertedTransactions = sb.toString();
                    sb = new StringBuilder();
                }
                sb.append(line).append(System.lineSeparator());
                line = br.readLine();
            }
            if (contentBeforeInsertedTransactions == null) {
                logger.warning("Could not find line starting with " + BEFORE_LINE_PREFIX + ". Appending records..");
                contentBeforeInsertedTransactions = sb.toString();
                contentAfterInsertedTransactions = "";
            } else {
                contentAfterInsertedTransactions = sb.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
