package com.mnemocon.sportsman.ai;

import org.joda.time.DateTime;

public class TimeUtils {

    // Возвращает день недели в коротком формате
    public static String getDay() {
        DateTime dt = new DateTime();
        DateTime.Property pDoW = dt.dayOfWeek();
        return pDoW.getAsShortText();
    }

    // Возвращает текущий месяц
    public static String getMonth() {
        DateTime dt = new DateTime();
        return dt.monthOfYear().getAsText();
    }

    // Возвращает текущую дату месяца
    public static String getDate() {
        DateTime dt = new DateTime();
        return dt.dayOfMonth().getAsText();
    }

    // Возвращает текущий год
    public static String getYear() {
        DateTime dt = new DateTime();
        int year = dt.getYear();
        return Integer.toString(year);
    }

    // Возвращает текущее время в формате 12 часов
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
