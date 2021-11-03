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

public class DKBCsvImporterTest {

    final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

    final String PROPERTY_FILE = "src/test/resources/importertests.properties";
    final String CSV_FILE = "src/test/resources/csv/dkb-csv-transactions.csv";

    @BeforeEach
    void setUp() {
    }

    @Test
    public void convertDKBCsvToTransactions() throws IOException {
        DFProperties prop = new DFProperties(PROPERTY_FILE);
        AccountRegistry.init(prop.readAccounts());

        File csv = new File(CSV_FILE);
        List<Transaction> transactions = FileReader.importCsv(AccountRegistry.get(3).getImporter(), csv);

        Assertions.assertTrue(transactions.size() == 3);
        Assertions.assertEquals("25-10-2021", simpleDateFormat.format(transactions.get(0).getDate()));
        Assertions.assertEquals("awv-meldepflicht beachten hotline bundesbank: (0800) 1234-111", transactions.get(0).getDescription());
        Assertions.assertEquals( 103.16, transactions.get(0).getValue());

        Assertions.assertEquals("22-10-2021", simpleDateFormat.format(transactions.get(1).getDate()));
        Assertions.assertEquals("2021-10-20t11:08 debitk.0 2099-12", transactions.get(1).getDescription());
        Assertions.assertEquals(-43.25, transactions.get(1).getValue());

        Assertions.assertEquals( "22-10-2021", simpleDateFormat.format(transactions.get(2).getDate()));
        Assertions.assertEquals( "2021-10-20t15:05 debitk.0 2099-12 einsatzentgelt 10,00 eur", transactions.get(2).getDescription());
        Assertions.assertEquals(-40.0, transactions.get(2).getValue());
    }
}
