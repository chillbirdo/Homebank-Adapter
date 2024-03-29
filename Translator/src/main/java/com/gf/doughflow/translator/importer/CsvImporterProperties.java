package com.gf.doughflow.translator.importer;

import com.gf.doughflow.translator.util.AmountConverter;

import java.util.Properties;

public class CsvImporterProperties extends Properties {

    private Integer headerLineAmount;
    private Integer columnAmount;
    private String separator;
    private Integer dateColumn;
    private Integer amountColumn;
    private AmountConverter.DigitPunctuation floatingPointCharacter;

    public Integer[] getDescriptionColumns() {
        return descriptionColumns;
    }

    public void setDescriptionColumns(Integer[] descriptionColumns) {
        this.descriptionColumns = descriptionColumns;
    }

    private Integer[] descriptionColumns;
    private String dateFormat;

    public CsvImporterProperties(Integer headerLineAmount, Integer columnAmount, String separator, Integer dateColumn, String dateFormat, Integer amountColumn, AmountConverter.DigitPunctuation floatingPointPunctuation, Integer[] descriptionColumns){
        this.headerLineAmount = headerLineAmount;
        this.columnAmount = columnAmount;
        this.separator = separator;
        this.dateColumn = dateColumn;
        this.dateFormat = dateFormat;
        this.amountColumn = amountColumn;
        this.floatingPointCharacter = floatingPointPunctuation;
        this.descriptionColumns = descriptionColumns;
    }

    public void setHeaderLineAmount(Integer headerLineAmount) {
        this.headerLineAmount = headerLineAmount;
    }

    public Integer getColumnAmount() {
        return columnAmount;
    }

    public void setColumnAmount(Integer columnAmount) {
        this.columnAmount = columnAmount;
    }

    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }

    public void setDateColumn(Integer dateColumn) {
        this.dateColumn = dateColumn;
    }

    public void setAmountColumn(Integer amountColumn) {
        this.amountColumn = amountColumn;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public Integer getHeaderLineAmount() {
        return headerLineAmount;
    }

    public Integer getDateColumn() {
        return dateColumn;
    }

    public Integer getAmountColumn() {
        return amountColumn;
    }

    public AmountConverter.DigitPunctuation getFloatingPointCharacter() {
        return floatingPointCharacter;
    }

    public void setFloatingPointCharacter(AmountConverter.DigitPunctuation floatingPointCharacter) {
        this.floatingPointCharacter = floatingPointCharacter;
    }
}
