package com.volgo34ivan.android.json;

public class StringUpperCase {
    public String upperCase(String in){
        String upperString = in.substring(0,1).toUpperCase() + in.substring(1);
        return upperString;
    }
}
