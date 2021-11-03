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

public class EasybankCsvImporterTest {

    final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

    final String PROPERTY_FILE = "src/test/resources/importertests.properties";
    final String EASYBANK_FILE = "src/test/resources/csv/EASYBANK_Umsatzliste_20211029_1709.csv";

    @BeforeEach
    void setUp() {
    }

    @Test
    public void convertEasybankCsvToTransactions() throws IOException {
        DFProperties prop = new DFProperties(PROPERTY_FILE);
        AccountRegistry.init(prop.readAccounts());

        File ebInputfile = new File(EASYBANK_FILE);
        List<Transaction> transactions = FileReader.importCsv(AccountRegistry.get(1).getImporter(), ebInputfile);

        Assertions.assertTrue(transactions.size() == 3);
        Assertions.assertEquals("28-10-2021", simpleDateFormat.format(transactions.get(0).getDate()));
        Assertions.assertEquals("bezahlung karte mc/000005253 5436 k004 28.10. 11:34 burger vegan \\\\wien\\1220", transactions.get(0).getDescription());
        Assertions.assertEquals( -8.6, transactions.get(0).getValue());
        Assertions.assertEquals("25-10-2021", simpleDateFormat.format(transactions.get(1).getDate()));
        Assertions.assertEquals("von bank austria sparkonto bg/000005243 bawaatwwxxx at7812831832813838832 ba herbert knolli", transactions.get(1).getDescription());
        Assertions.assertEquals(-20000.01, transactions.get(1).getValue());
        Assertions.assertEquals( "25-10-2021", simpleDateFormat.format(transactions.get(2).getDate()));
        Assertions.assertEquals( "sb-eigenerlag bg/000005242 25.10.2021 um 11:50 / 0500 sb-geraet bawag psk fil. 001", transactions.get(2).getDescription());
        Assertions.assertEquals(2800, transactions.get(2).getValue());
    }
}
