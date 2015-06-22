package com.gf.doughflow.translator.exporter;

import com.gf.doughflow.translator.importer.FileImporter;
import com.gf.doughflow.translator.importer.XhbImporter;
import com.gf.doughflow.translator.model.Transaction;
import com.gf.doughflow.util.JulianDate;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class XhbInsertingExporter extends XhbExporter {

    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private final String BEFORE_LINE_PREFIX = "</homebank>";

    public File xhbInputfile;
    private String contentBefore = null;
    private String contentAfter = null;

    public XhbInsertingExporter(File insertFile) {
        super();
        this.xhbInputfile = insertFile;
        initContentBeforeAfter();
    }

    public List<Transaction> filterDuplicates(List<Transaction> freshTransactions) {
        XhbImporter xhbImporter = new XhbImporter();
        List<Transaction> xhbTransactions = FileImporter.importRecordPerLine(xhbImporter, xhbInputfile);

        Map<String, Transaction> xhbTransactionMap = new HashMap<String, Transaction>();
        for (Transaction t : xhbTransactions) {
            xhbTransactionMap.put(extractKey(t), t);
        }
        List<Transaction> nonDuplicates = new ArrayList<Transaction>();
        for (Transaction ebt : freshTransactions) {
            if (xhbTransactionMap.get(extractKey(ebt)) != null) {
                continue;
            }
            nonDuplicates.add(ebt);
        }
        return nonDuplicates;
    }

    private String extractKey(Transaction t) {
        return t.getAccount().getId() + JulianDate.dateToJulian(t.getDate()) + t.getDescription();
    }

    @Override
    public String createHeader() {
        return contentBefore;
    }

    @Override
    public String createTrailer() {
        return contentAfter;
    }

    public void initContentBeforeAfter() {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(xhbInputfile));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                if (line.startsWith(BEFORE_LINE_PREFIX)) {
                    contentBefore = sb.toString();
                    sb = new StringBuilder();
                }
                sb.append(line).append(System.lineSeparator());
                line = br.readLine();
            }
            if (contentBefore == null) {
                logger.warning("Could not find line starting with " + BEFORE_LINE_PREFIX + ". Appending records..");
                contentBefore = sb.toString();
                contentAfter = "";
            } else {
                contentAfter = sb.toString();
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
