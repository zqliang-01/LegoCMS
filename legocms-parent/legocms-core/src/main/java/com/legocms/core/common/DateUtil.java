package com.legocms.core.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

public class DateUtil extends DateUtils {
    /**
     * yyyy-MM-dd
     */
    public static final String datePattern = "yyyy-MM-dd";
    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static final String dateTimePattern = "yyyy-MM-dd HH:mm:ss";

    public static final int dateTimePatternLength = dateTimePattern.length();
    public static final int datePatternLength = datePattern.length();

    public static String toDateString(Date date) {
        return toDateString(date, datePattern);
    }

    public static String toDateTimeString(Date date) {
        return toDateString(date, dateTimePattern);
    }

    public static String toDateString(Date date, String pattern) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }

    public static Date toDateTime(String dateTime) throws ParseException {
        if (StringUtil.isBlank(dateTime)) {
            return null;
        }
        return new SimpleDateFormat(dateTimePattern).parse(dateTime);
    }

    public static Date toDate(String date) throws ParseException {
        if (StringUtil.isBlank(date)) {
            return null;
        }
        return new SimpleDateFormat(datePattern).parse(date);
    }

    public static Date toDate(long date) {
        return new Date(date);
    }

    public static Date getCurrentDate() {
        return new Date();
    }
}
