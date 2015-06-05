package com.gf.doughflow.translator.exporter;

import com.gf.doughflow.translator.model.Transaction;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class QifExporter implements IExporter {

    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
    private final String HEADER = "!Type:Bank";
    private final DecimalFormat df = new DecimalFormat("##.##");
    
    @Override
    public String createHeader() {
        return HEADER;
    }

    @Override
    public String export(Transaction transaction) {
        return new String().concat(convertDate(transaction.getDate()) + System.lineSeparator())
                .concat(convertValue(transaction.getValue()) + System.lineSeparator())
                .concat(convertDescription(transaction.getDescription()) + System.lineSeparator())
                .concat("^" + System.lineSeparator());
    }

    @Override
    public String createTrailer() {
        return null;
    }

    private String replaceGermanLetters(String description) {
        return description.toLowerCase().replaceAll("ä", "ae").replaceAll("ö", "oe").replaceAll("ü", "ue").replaceAll("ß", "ss");
    }

    private String convertDescription(String description) {
        return "M" + replaceGermanLetters(description);
    }

    private String convertValue(Double value) {
        return "T" + df.format(value);
    }

    private String convertDate(Date d) {
        return "D" + sdf.format(d);
    }

}
