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

public class DKBCreditcardCsvImporterTest {

    final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

    final String PROPERTY_FILE = "src/test/resources/importertests.properties";
    final String CSV_FILE = "src/test/resources/csv/dkb-creditcard-csv-transactions.csv";

    @BeforeEach
    void setUp() {
    }

    @Test
    public void convertDKBCsvToTransactions() throws IOException {
        DFProperties prop = new DFProperties(PROPERTY_FILE);
        AccountRegistry.init(prop.readAccounts());

        File csv = new File(CSV_FILE);
        List<Transaction> transactions = FileReader.importCsv(AccountRegistry.get(6).getImporter(), csv);

        Assertions.assertTrue(transactions.size() == 3);
        Assertions.assertEquals("27-01-2022", simpleDateFormat.format(transactions.get(0).getDate()));
        Assertions.assertEquals("ficanovaria", transactions.get(0).getDescription());
        Assertions.assertEquals( -9.74, transactions.get(0).getValue());

        Assertions.assertEquals("21-01-2022", simpleDateFormat.format(transactions.get(1).getDate()));
        Assertions.assertEquals("ausgleich kreditkarte gem. abrechnung v. 21.01.22", transactions.get(1).getDescription());
        Assertions.assertEquals(37.15, transactions.get(1).getValue());

        Assertions.assertEquals( "30-12-2021", simpleDateFormat.format(transactions.get(2).getDate()));
        Assertions.assertEquals( "amazon *mktplce eu-xzc", transactions.get(2).getDescription());
        Assertions.assertEquals(-29.16, transactions.get(2).getValue());
    }
}
