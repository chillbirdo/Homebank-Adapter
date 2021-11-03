package com.gf.doughflow.translator.importer;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class DKBCsvImporter extends BaseCsvImporter {

    private final Charset CHARSET = StandardCharsets.ISO_8859_1;
    private final int HEADER_LINES = 7;
    private final int COLUMNS = 11;
    private final String DELIMITER = ";";
    private final int COLUMN_DATE = 0;
    private final String DATE_FORMAT = "dd.MM.yyyy";
    private final int COLUMN_DESCRIPTION = 4;
    private final int COLUMN_AMOUNT = 7;

    private final CsvImporterProperties csvImporterProperties = new CsvImporterProperties(
            HEADER_LINES, COLUMNS, DELIMITER, COLUMN_DATE, DATE_FORMAT, COLUMN_AMOUNT, COLUMN_DESCRIPTION);

    public DKBCsvImporter(int accountIdInWorkspace){
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
