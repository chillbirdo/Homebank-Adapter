package com.gf.doughflow.translator.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AmountConverterTest {

    @Test
    public void testConvertAmount(){
        Assertions.assertEquals(1000.1, AmountConverter.convertAmount("1,000.1", AmountConverter.DigitPunctuation.PERIOD));
        Assertions.assertEquals(1000.1, AmountConverter.convertAmount("1000.1", AmountConverter.DigitPunctuation.PERIOD));
        Assertions.assertEquals(1000.1, AmountConverter.convertAmount("1.000,1", AmountConverter.DigitPunctuation.COMMA));
        Assertions.assertEquals(1000.1, AmountConverter.convertAmount("1000,1", AmountConverter.DigitPunctuation.COMMA));
    }
}
