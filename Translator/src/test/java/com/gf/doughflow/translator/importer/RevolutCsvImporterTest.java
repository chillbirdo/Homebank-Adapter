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

public class RevolutCsvImporterTest {

    final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

    final String PROPERTY_FILE = "src/test/resources/importertests.properties";
    final String CSV_FILE = "src/test/resources/csv/revolut-csv-transactions.csv";

    @BeforeEach
    void setUp() {
    }

    @Test
    public void convertRevolutCsvToTransactions() throws IOException {
        DFProperties prop = new DFProperties(PROPERTY_FILE);
        AccountRegistry.init(prop.readAccounts());

        File csv = new File(CSV_FILE);
        List<Transaction> transactions = FileReader.importCsv(AccountRegistry.get(4).getImporter(), csv);

        Assertions.assertTrue(transactions.size() == 3);
        Assertions.assertEquals("20-10-2024", simpleDateFormat.format(transactions.get(0).getDate()));
        Assertions.assertEquals("to chillbird - hello - chillbird", transactions.get(0).getDescription());
        Assertions.assertEquals( -50.51, transactions.get(0).getValue());

        Assertions.assertEquals("15-10-2024", simpleDateFormat.format(transactions.get(1).getDate()));
        Assertions.assertEquals("to chillbird - private stuff - chillbird", transactions.get(1).getDescription());
        Assertions.assertEquals(-3000.11, transactions.get(1).getValue());

        Assertions.assertEquals( "09-10-2024", simpleDateFormat.format(transactions.get(2).getDate()));
        Assertions.assertEquals( "to chillbird - gifts - chillbird", transactions.get(2).getDescription());
        Assertions.assertEquals(1000, transactions.get(2).getValue());
    }
}
