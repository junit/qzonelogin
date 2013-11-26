package org.chinasb.example.qzonelogin.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    public static boolean isBlank(String s) {
        boolean b = false;
        if (s == null || "".equals(s)) {
            b = true;
        }
        return b;
    }

    public static String findPattern(String expression, String content) {
        String s = null;
        Pattern p = Pattern.compile(expression);
        Matcher m = p.matcher(content);
        if (m.find()) {
            s = m.group();
        }
        return s;
    }

    public static boolean exist(String s, String content) {
        Pattern p = Pattern.compile(s);
        Matcher m = p.matcher(content);
        if (m.find()) {
            return true;
        } else {
            return false;
        }
    }
}
