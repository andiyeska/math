package com.example.math.utility;

public class StringUtil {

    public static boolean isNotNullAndNotEmpty(String token) {
        return token != null && !token.isEmpty();
    }
    public static boolean isnullOrEmpty(String token) {
        if(token != null) token = token.replaceAll("\\s+","");
        else return Boolean.TRUE;

        return token.isEmpty();
    }

}
