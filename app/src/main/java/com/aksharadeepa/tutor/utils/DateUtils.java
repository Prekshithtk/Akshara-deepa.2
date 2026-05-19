package com.aksharadeepa.tutor.utils;

import java.util.Calendar;

public final class DateUtils {
    private DateUtils() {}

    public static long startOfToday() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTimeInMillis();
    }

    public static long dayKey() {
        return startOfToday() / 86_400_000L;
    }
}
