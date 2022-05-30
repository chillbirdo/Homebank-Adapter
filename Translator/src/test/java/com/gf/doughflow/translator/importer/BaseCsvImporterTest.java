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

public class BaseCsvImporterTest {

    final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

    final String PROPERTY_FILE = "src/test/resources/importertests-with-irgnorebeforedate.properties";
    final String CSV_FILE = "src/test/resources/csv/EASYBANK_Umsatzliste_20211029_1709.csv";

    @BeforeEach
    void setUp() {
    }

    @Test
    public void convertDKBCsvToTransactions() throws IOException {
        DFProperties prop = new DFProperties(PROPERTY_FILE);
        AccountRegistry.init(prop.readAccounts());

        File ebInputfile = new File(CSV_FILE);
        List<Transaction> transactions = FileReader.importCsv(AccountRegistry.get(1).getImporter(), ebInputfile);

        Assertions.assertEquals(1, transactions.size());
        Assertions.assertEquals("28-10-2021", simpleDateFormat.format(transactions.get(0).getDate()));
        Assertions.assertEquals("bezahlung karte mc/000005253 5436 k004 28.10. 11:34 burger vegan \\\\wien\\1220", transactions.get(0).getDescription());
        Assertions.assertEquals( -8.6, transactions.get(0).getValue());
    }
}
