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
        Assertions.assertEquals("14-10-2024", simpleDateFormat.format(transactions.get(0).getDate()));
        Assertions.assertEquals("amazon..mktplce.eu.de/8087 - visa debitkartenumsatz", transactions.get(0).getDescription());
        Assertions.assertEquals( -68.17, transactions.get(0).getValue());

        Assertions.assertEquals("01-10-2024", simpleDateFormat.format(transactions.get(1).getDate()));
        Assertions.assertEquals("dkb ag - abrechnung 30.09.2024 siehe anlage abrechnung 30.09.2024 information zur abrechnung kontostand am 30.09.2024 505,21 + abrechnungszeitraum vom 01.07.2024 bis 30.09.2024 abrechnung 30.09.2024 0,00+ sollzinssaetze am 30.09.2024 10,5000 v.h. fuer eingeraeumte kontoueberziehung (aktuell eingeraeumte kontoueberziehung 100,00) 10,5000 v.h. fuer geduldete kontoueberziehung ueber die eingeraeumte kontoueberziehung hinaus kontostand/rechnungsabschluss am 30.09.2024 505,21 + rechnungsnummer: 20240930-by111-00210137491", transactions.get(1).getDescription());
        Assertions.assertEquals(0.0, transactions.get(1).getValue());

        Assertions.assertEquals( "16-09-2024", simpleDateFormat.format(transactions.get(2).getDate()));
        Assertions.assertEquals( "amazon - visa debitkartenumsatz", transactions.get(2).getDescription());
        Assertions.assertEquals(40.0, transactions.get(2).getValue());
    }
}
