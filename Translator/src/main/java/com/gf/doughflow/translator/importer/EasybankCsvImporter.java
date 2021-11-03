package com.gf.doughflow.translator.importer;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class EasybankCsvImporter extends BaseCsvImporter {

    private final Charset CHARSET = StandardCharsets.ISO_8859_1;
    private final int HEADER_LINES = 0;
    private final int COLUMNS = 6;
    private final String DELIMITER = ";";
    private final int COLUMN_DATE = 3;
    private final int COLUMN_DESCRIPTION = 1;
    private final int COLUMN_AMOUNT = 4;
    private final String DATE_FORMAT = "dd.MM.yyyy";

    private final CsvImporterProperties csvImporterProperties = new CsvImporterProperties(
            HEADER_LINES, COLUMNS, DELIMITER, COLUMN_DATE, DATE_FORMAT, COLUMN_AMOUNT, COLUMN_DESCRIPTION);

    public EasybankCsvImporter(int accountIdInWorkspace){
        super(accountIdInWorkspace);
        super.charset = CHARSET;
        super.csvImporterProperties = csvImporterProperties;
    }

    @Override
    protected Double convertAmount(String ds) {
        String dsPeriod = ds.replaceAll("\\.", "").replaceAll(",", ".");
        return Double.valueOf(dsPeriod);
    }
}
