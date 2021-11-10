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

public class FlatexCsvImporterTest {

    final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

    final String PROPERTY_FILE = "src/test/resources/importertests.properties";
    final String CSV_FILE = "src/test/resources/csv/flatex-csv-transactions.csv";

    @BeforeEach
    void setUp() {
    }

    @Test
    public void convertEasybankCsvToTransactions() throws IOException {
        DFProperties prop = new DFProperties(PROPERTY_FILE);
        AccountRegistry.init(prop.readAccounts());

        File csv = new File(CSV_FILE);
        List<Transaction> transactions = FileReader.importCsv(AccountRegistry.get(5).getImporter(), csv);

        Assertions.assertTrue(transactions.size() == 3);
        Assertions.assertEquals("09-11-2021", simpleDateFormat.format(transactions.get(0).getDate()));
        Assertions.assertEquals("elektrizitaets-ag", transactions.get(0).getDescription());
        Assertions.assertEquals( -63.46, transactions.get(0).getValue());

        Assertions.assertEquals("01-11-2021", simpleDateFormat.format(transactions.get(1).getDate()));
        Assertions.assertEquals("", transactions.get(1).getDescription());
        Assertions.assertEquals(144.39, transactions.get(1).getValue());

        Assertions.assertEquals( "22-10-2021", simpleDateFormat.format(transactions.get(2).getDate()));
        Assertions.assertEquals( "fonds fonds / fonds", transactions.get(2).getDescription());
        Assertions.assertEquals(-38900.83, transactions.get(2).getValue());
    }
}
