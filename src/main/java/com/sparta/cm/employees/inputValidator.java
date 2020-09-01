package com.sparta.cm.employees;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class inputValidator {
    public static boolean isIDValid(String[] input){
        if (input[0] == null){
            return false;
        }
        else return true;
    }
    public static LocalDate convertToDate(String input)throws NumberFormatException, DateTimeParseException{
        String[] s = input.split("/");
        int year = Integer.parseInt(s[2]);
        int month = Integer.parseInt(s[0]);
        int day = Integer.parseInt(s[1]);
        LocalDate date = LocalDate.of(year, month, day);
        return date;
    }

    public static boolean isEmailValid(String[] input) {
        String email = input[6];
        Pattern p = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(email);
        if(m.find()){
            return true;
        }else{
            return false;
        }
    }
}
