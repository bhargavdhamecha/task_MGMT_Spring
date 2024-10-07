package com.task.management.shared.utils;

public class StringUtils {

    private StringUtils(){}

    public static String getDomainNameFromEmail(String email){
        String[] parts = email.split("@");
        if (parts.length == 2){
            return parts[1];
        }
        return "";
    }
}
