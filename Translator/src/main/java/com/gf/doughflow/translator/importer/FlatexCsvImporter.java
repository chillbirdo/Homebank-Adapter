package com.gf.doughflow.translator.importer;

import com.gf.doughflow.translator.util.AmountConverter;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class FlatexCsvImporter extends BaseCsvImporter {

    private final Charset CHARSET = StandardCharsets.ISO_8859_1;
    private final int HEADER_LINES = 1;
    private final int COLUMNS = 11;
    private final String DELIMITER = ";";
    private final int COLUMN_DATE = 1;
    private final Integer[] COLUMNS_DESCRIPTION = new Integer[]{4};
    private final int COLUMN_AMOUNT = 6;
    private final AmountConverter.DigitPunctuation FLOATINGPOINT_PUNCTUATION = AmountConverter.DigitPunctuation.COMMA;
    private final String DATE_FORMAT = "dd.MM.yyyy";

    private final CsvImporterProperties csvImporterProperties = new CsvImporterProperties(
            HEADER_LINES, COLUMNS, DELIMITER, COLUMN_DATE, DATE_FORMAT, COLUMN_AMOUNT, FLOATINGPOINT_PUNCTUATION, COLUMNS_DESCRIPTION);

    public FlatexCsvImporter(int accountIdInWorkspace){
        super(accountIdInWorkspace);
        super.charset = CHARSET;
        super.csvImporterProperties = csvImporterProperties;
    }
}
