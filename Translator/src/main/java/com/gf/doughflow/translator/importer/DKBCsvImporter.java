package com.gf.doughflow.translator.importer;

import com.gf.doughflow.translator.util.AmountConverter;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class DKBCsvImporter extends BaseCsvImporter {

    private final Charset CHARSET = StandardCharsets.UTF_8;
    private final int HEADER_LINES = 5;
    private final int COLUMNS = 12;
    private final String DELIMITER = ",";
    private final int COLUMN_DATE = 0;
    private final String DATE_FORMAT = "dd.MM.yy";
    private final Integer[] COLUMNS_DESCRIPTION = new Integer[]{4,5};
    private final int COLUMN_AMOUNT = 8;
    private final AmountConverter.DigitPunctuation FLOATINGPOINT_PUNCTUATION = AmountConverter.DigitPunctuation.COMMA;

    private final CsvImporterProperties csvImporterProperties = new CsvImporterProperties(
            HEADER_LINES, COLUMNS, DELIMITER, COLUMN_DATE, DATE_FORMAT, COLUMN_AMOUNT, FLOATINGPOINT_PUNCTUATION, COLUMNS_DESCRIPTION);

    public DKBCsvImporter(int accountIdInWorkspace){
        super(accountIdInWorkspace);
        super.charset = CHARSET;
        super.csvImporterProperties = csvImporterProperties;
    }
}
