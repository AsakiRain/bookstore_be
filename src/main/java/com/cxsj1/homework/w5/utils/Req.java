package com.cxsj1.homework.w5.utils;


public class Req {
    public static boolean hasEmpty(Object... items) {
        for (Object item : items) {
            if (item instanceof String) {
                if (((String) item).strip().isBlank()) {
                    return true;
                }
            } else if (item == null) {
                return true;
            }
        }
        return false;
    }
}
