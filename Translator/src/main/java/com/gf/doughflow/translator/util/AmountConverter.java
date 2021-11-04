package com.gf.doughflow.translator.util;

public class AmountConverter {

    public static enum DigitPunctuation {
        COMMA,
        PERIOD
    }

    /**
     * automatically normalized the following formats: "1.000,50", "1,000.50", "1000,50", "1000.50"
     * @param amountStr
     * @param floatingPointCharacter
     * @return
     */
    public static Double convertAmount(String amountStr, DigitPunctuation floatingPointCharacter){
        String normalizedAmountStr = amountStr;
        switch (floatingPointCharacter){
            case COMMA:
                normalizedAmountStr = amountStr.replaceAll("\\.", "").replaceAll(",", ".");
                break;
            case PERIOD:
                normalizedAmountStr = amountStr.replaceAll(",", "");
                break;
        }
        return Double.valueOf(normalizedAmountStr);
    }
}
