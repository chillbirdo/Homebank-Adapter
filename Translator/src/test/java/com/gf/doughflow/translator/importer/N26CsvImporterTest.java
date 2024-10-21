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
        Assertions.assertEquals("12-10-2022", simpleDateFormat.format(transactions.get(0).getDate()));
        Assertions.assertEquals("miete+strom+internet - 5x90 plus 50 nacken", transactions.get(0).getDescription());
        Assertions.assertEquals( -500.12, transactions.get(0).getValue());

        Assertions.assertEquals("15-10-2022", simpleDateFormat.format(transactions.get(1).getDate()));
        Assertions.assertEquals("miete+strom+internet - schwangerschaft sachen bh", transactions.get(1).getDescription());
        Assertions.assertEquals(55.00, transactions.get(1).getValue());

        Assertions.assertEquals( "01-11-2022", simpleDateFormat.format(transactions.get(2).getDate()));
        Assertions.assertEquals( "miete+strom+internet - vitamin schwanger", transactions.get(2).getDescription());
        Assertions.assertEquals(-111140.0, transactions.get(2).getValue());
    }
}
