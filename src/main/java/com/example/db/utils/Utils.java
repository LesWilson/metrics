package com.example.db.utils;

import org.jsoup.Jsoup;

import static org.jsoup.parser.Parser.unescapeEntities;

public class Utils {

    public static String sanitize(String original) {
        System.out.println(Jsoup.parseBodyFragment(original).text());
        return Jsoup.parse(original).text();
//        return unescapeUntilNoHtmlEntityFound(original);
    }

    private static String unescapeUntilNoHtmlEntityFound(final String value){
        String unescaped = unescapeEntities(value, false);
        System.out.println("value:" + value + " unescaped:"+unescaped);
        if ( !unescaped.equals(value) ) {
            return unescapeUntilNoHtmlEntityFound(unescaped);
        }
        return unescaped;
    }

}
