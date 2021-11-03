package com.gf.doughflow.translator.util;

public class DescriptionConverter {

    public static String removeQuotes(String input){
        return input.replaceAll("\"", "");
    }

    public static String removeSpaces(String input) {
        String ret = input;
        while (ret.contains("  ")) {
            ret = ret.replaceAll("  ", " ");
        }
        return ret.trim();
    }

    public static String replaceGermanLetters(String description) {
        return description.toLowerCase().replaceAll("ä", "ae").replaceAll("ö", "oe").replaceAll("ü", "ue").replaceAll("ß", "ss");
    }

}
