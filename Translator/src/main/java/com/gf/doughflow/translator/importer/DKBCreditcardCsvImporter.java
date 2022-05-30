package com.gf.doughflow.translator.importer;

import com.gf.doughflow.translator.util.AmountConverter;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class DKBCreditcardCsvImporter extends BaseCsvImporter {

    private final Charset CHARSET = StandardCharsets.ISO_8859_1;
    private final int HEADER_LINES = 8;
    private final int COLUMNS = 6;
    private final String DELIMITER = ";";
    private final int COLUMN_DATE = 2;
    private final String DATE_FORMAT = "dd.MM.yyyy";
    private final Integer[] COLUMNS_DESCRIPTION = new Integer[]{3};
    private final int COLUMN_AMOUNT = 4;
    private final AmountConverter.DigitPunctuation FLOATINGPOINT_PUNCTUATION = AmountConverter.DigitPunctuation.COMMA;

    private final CsvImporterProperties csvImporterProperties = new CsvImporterProperties(
            HEADER_LINES, COLUMNS, DELIMITER, COLUMN_DATE, DATE_FORMAT, COLUMN_AMOUNT, FLOATINGPOINT_PUNCTUATION, COLUMNS_DESCRIPTION);

    public DKBCreditcardCsvImporter(int accountIdInWorkspace){
        super(accountIdInWorkspace);
        super.charset = CHARSET;
        super.csvImporterProperties = csvImporterProperties;
    }
}
