package com.gf.doughflow.util;

import java.util.Date;

public class JulianDate {

    private static final int OFFSET = 719164;
    private static final int FACTOR_MS_TO_DAYS = 1000*60*60*24;
    
    public static int dateToJulian( Date d){
        long days = d.getTime() / FACTOR_MS_TO_DAYS;
        return OFFSET + (int) days;
    }
    
    public static Date julianToDate( int julian){
        long days = julian - OFFSET;
        return new Date(days * FACTOR_MS_TO_DAYS);
    }
}
