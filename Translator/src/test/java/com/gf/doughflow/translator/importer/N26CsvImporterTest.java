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
        Assertions.assertEquals("02-07-2019", simpleDateFormat.format(transactions.get(0).getDate()));
        Assertions.assertEquals("july", transactions.get(0).getDescription());
        Assertions.assertEquals( 22.12, transactions.get(0).getValue());
        Assertions.assertEquals("03-07-2019", simpleDateFormat.format(transactions.get(1).getDate()));
        Assertions.assertEquals("abcd aeefg", transactions.get(1).getDescription());
        Assertions.assertEquals(0.01, transactions.get(1).getValue());
        Assertions.assertEquals( "05-07-2019", simpleDateFormat.format(transactions.get(2).getDate()));
        Assertions.assertEquals( "strom - 1 )(----///23123+--=qaeue", transactions.get(2).getDescription());
        Assertions.assertEquals(-8.0, transactions.get(2).getValue());
    }
}
