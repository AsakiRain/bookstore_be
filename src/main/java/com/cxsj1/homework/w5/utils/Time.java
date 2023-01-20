package com.cxsj1.homework.w5.utils;

import java.util.Calendar;
import java.util.Date;

public class Time {
    public static Date elapse(int scope, int delta) {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        int current = c.get(scope);
        c.set(scope, current + delta);

        return c.getTime();
    }

    public static Date elapseDay(int delta) {
        return Time.elapse(Calendar.DAY_OF_MONTH, delta);
    }

    public static Date elapseMin(int delta) {
        return Time.elapse(Calendar.MINUTE, delta);
    }
}
