package com.insightsurface.lib.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/9/5.
 */

public class DateTranslateUtil {
    public static String getFormatedDateTime(String pattern, long dateTime) {
        SimpleDateFormat sDateFormat = new SimpleDateFormat(pattern);
        return sDateFormat.format(new Date(dateTime + 0));
    }

    public static long getCurrentDateSeconds() {
        Date dt = new Date();
        return dt.getTime();
    }

    public static String formatDate(long date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(new Date(date));
    }

    public static String getDateByAddDays(int days) {
        long currentSeconds = getCurrentDateSeconds();
        long addSeconds = days * 84000 * 1000;
        long totalSeconds = currentSeconds + addSeconds;

        return getFormatedDateTime("yyyy-MM-dd", totalSeconds);
    }
}
