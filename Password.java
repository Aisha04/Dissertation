package com.example.train;

import java.util.regex.Pattern;

public class Password {
    public static final Pattern PASSWORD_PATTERN = Pattern.compile("^" +
            "(?=.*[0-9])" + // minimum one number
                  "(?=.*[a-z])" + //minimum one Lower case
                "(?=.*[A-Z])" + //minimum one upper case
               "(?=.*[@#$%^&-+=()])" + //minimum one special character
             "(?=\\\\S+$)" +//no white space
            ".{8,20}" + //minimum length 6 characters
           "$");

}
