package com.gf.doughflow.translator.util;

import java.util.Date;

public class HomebankDate {

    private static final int HOMEBANK_OFFSET = 719164;
    private static final int FACTOR_MS_TO_DAYS = 1000*60*60*24;

    public static int dateToHomebankDate(Date d){
        long days = d.getTime() / FACTOR_MS_TO_DAYS;
        return HOMEBANK_OFFSET + (int) days;
    }

    public static Date homebankDateToDate(int julian){
        long days = julian - HOMEBANK_OFFSET;
        return new Date(days * FACTOR_MS_TO_DAYS);
    }
}
