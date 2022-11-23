package com.cxsj1.homework.w4.utils;


public class Req {
    public static boolean hasEmpty(String... items) {
        for (String item : items) {
            if (item == null || item.strip().isBlank()) {
                return true;
            }
        }
        return false;
    }
}
