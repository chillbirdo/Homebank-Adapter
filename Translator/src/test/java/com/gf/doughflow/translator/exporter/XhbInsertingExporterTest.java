package com.gf.doughflow.translator.exporter;

import com.gf.doughflow.translator.importer.FileReader;
import com.gf.doughflow.translator.model.Transaction;
import com.gf.doughflow.workspace.AccountRegistry;
import com.gf.doughflow.workspace.DFProperties;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class XhbInsertingExporterTest {

    final String PROPERTY_FILE = "src/test/resources/importertests.properties";
    final String XHB_EASYBANK_ACTUAL_FILE = "src/test/resources/xhb/actual_TestXhbInsertingExporter.xhb";
    final String CSV_EASYBANK_ACTUAL_FILE = "src/test/resources/csv/EASYBANK_TextXhbInsertingExporter.csv";

    File xhb = new File(XHB_EASYBANK_ACTUAL_FILE);
    File csv = new File(CSV_EASYBANK_ACTUAL_FILE);

    @Test
    public void testInsertingCheckNonDuplicates() throws IOException {
        DFProperties prop = new DFProperties(PROPERTY_FILE);
        AccountRegistry.init(prop.readAccounts());

        List<Transaction> freshTransactions = FileReader.importCsv(AccountRegistry.get(1).getImporter(), csv);
        XhbInsertingExporter xhbInsertingExporter = new XhbInsertingExporter(xhb);
        List<Transaction> nonDuplicates = xhbInsertingExporter.filterDuplicates(freshTransactions);

        Assertions.assertEquals(2, nonDuplicates.size());
    }


}
