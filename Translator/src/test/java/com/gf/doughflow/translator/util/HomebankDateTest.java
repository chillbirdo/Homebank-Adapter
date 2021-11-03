package com.gf.doughflow.translator.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class HomebankDateTest {

    @Test
    public void dateToHomebank(){
        Date date = new GregorianCalendar(2021, Calendar.OCTOBER, 29).getTime();
        int homebankDate = HomebankDate.dateToHomebankDate(date);

        Assertions.assertEquals(738092, homebankDate);
    }
}
