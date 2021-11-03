package com.gf.doughflow.translator.importer;

import com.gf.doughflow.translator.model.Transaction;
import com.gf.doughflow.workspace.AccountRegistry;
import com.gf.doughflow.workspace.DFProperties;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

public class N26CsvImporterTest {

    final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

    final String PROPERTY_FILE = "src/test/resources/importertests.properties";
    final String CSV_FILE = "src/test/resources/csv/n26-csv-transactions.csv";

    @BeforeEach
    void setUp() {
    }

    @Test
    public void convertN26CsvToTransactions() throws IOException {
        DFProperties prop = new DFProperties(PROPERTY_FILE);
        AccountRegistry.init(prop.readAccounts());

        File csv = new File(CSV_FILE);
        List<Transaction> transactions = FileReader.importCsv(AccountRegistry.get(2).getImporter(), csv);

        Assertions.assertTrue(transactions.size() == 3);
        Assertions.assertEquals("19-08-2017", simpleDateFormat.format(transactions.get(0).getDate()));
        Assertions.assertEquals("00011131311", transactions.get(0).getDescription());
        Assertions.assertEquals( -34.0, transactions.get(0).getValue());
        Assertions.assertEquals("22-08-2017", simpleDateFormat.format(transactions.get(1).getDate()));
        Assertions.assertEquals("miete sept", transactions.get(1).getDescription());
        Assertions.assertEquals(940, transactions.get(1).getValue());
        Assertions.assertEquals( "05-09-2017", simpleDateFormat.format(transactions.get(2).getDate()));
        Assertions.assertEquals( "upc rechnung 211121321 aenderung", transactions.get(2).getDescription());
        Assertions.assertEquals(-27.85, transactions.get(2).getValue());
    }
}
