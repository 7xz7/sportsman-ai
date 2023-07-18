package com.mnemocon.sportsman.ai;

import org.joda.time.DateTime;

public class TimeUtils {
    public static String getDay() {
        DateTime dt = new DateTime();
        DateTime.Property pDoW = dt.dayOfWeek();
        //        String strT = pDoW.getAsText(); // returns "Monday", "Tuesday", etc.
        return pDoW.getAsShortText();
    }

    public static String getMonth() {
        DateTime dt = new DateTime();
        return dt.monthOfYear().getAsText();
    }

    public static  String getDate() {
        DateTime dt = new DateTime();
        return dt.dayOfMonth().getAsText();
    }

    public static String getYear() {
        DateTime dt = new DateTime();
        int year = dt.getYear();
        return Integer.toString(year);
    }

    public static String getTime() {
        DateTime dt = new DateTime();
        int hour = dt.getHourOfDay();
        int min = dt.getMinuteOfHour();
        String ans = "";
        if( hour >= 12 ) {
            if( hour > 12 ) hour -= 12;
            ans += Integer.toString(hour);
            ans += ":";
            if( Integer.toString(min).length() == 1 ) ans += "0" + min;
            else ans += Integer.toString(min);
            ans += "pm";
        }
        else {
            if(hour == 0) hour = 12;
            ans += Integer.toString(hour);
            ans += ":";
            if( Integer.toString(min).length() == 1 ) ans += "0" + min;
            else ans += Integer.toString(min);
            ans += "am";
        }
        return ans;
    }
}
