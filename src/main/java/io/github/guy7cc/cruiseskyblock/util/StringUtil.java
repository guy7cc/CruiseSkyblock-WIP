package io.github.guy7cc.cruiseskyblock.util;

import java.util.UUID;

public class StringUtil {
    public static String ordinal(int n) {
        final String[] suf = new String[]{"th", "st", "nd", "rd"};
        return n + suf[n / 10 % 10 == 1 || n % 10 > 3 ? 0 : n % 10];
    }
}
