package com.cxsj1.homework.w5.utils;

import java.util.Arrays;

public class URLPath {
    public String prefix;
    public boolean verbose;

    public URLPath(String prefix, boolean verbose) {
        this.prefix = prefix;
        this.verbose = verbose;
    }

    public boolean match(String path, String pattern) {
        if (!path.startsWith(this.prefix)) {
            return false;
        }
        String[] pathArr = path.replace(this.prefix, "").split("/");
        String[] patternArr = pattern.split("/");
        if (verbose)
            System.out.printf("::pathArr: %s; patternArr: %s\n", Arrays.toString(pathArr), Arrays.toString(patternArr));
        if (pathArr.length < patternArr.length) {
            // 如果pathArr比patternArr短，说明path没有进入pattern匹配范围
            // 例如：pathArr=["", "user"]，patternArr=["", "user", "info"]
            return false;
        }
        for (int i = 0; i < patternArr.length; i++) {
            // 从前往后一段段匹配，如果其中一段不匹配，说明path不在pattern匹配范围内
            // pathArr在前，是因为pathArr比patternArr长的部分不需要匹配
            if (!patternArr[i].equals("*") && !pathArr[i].equals(patternArr[i])) {
                return false;
            }
        }
        return true;
    }
}
