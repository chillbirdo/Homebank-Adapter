package com.gf.doughflow.translator.importer;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class RevolutCsvImporter extends BaseCsvImporter {

    private final Charset CHARSET = StandardCharsets.UTF_8;
    private final int HEADER_LINES = 1;
    private final int COLUMNS = 21;
    private final String DELIMITER = ",";
    private final int COLUMN_DATE = 0;
    private final String DATE_FORMAT = "yyyy-MM-dd";
    private final int COLUMN_DESCRIPTION = 7;
    private final int COLUMN_AMOUNT = 11;

    private final CsvImporterProperties csvImporterProperties = new CsvImporterProperties(
            HEADER_LINES, COLUMNS, DELIMITER, COLUMN_DATE, DATE_FORMAT, COLUMN_AMOUNT, COLUMN_DESCRIPTION);

    public RevolutCsvImporter(int accountIdInWorkspace){
        super(accountIdInWorkspace);
        super.charset = CHARSET;
        super.csvImporterProperties = csvImporterProperties;
    }
}
