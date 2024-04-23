package io.github.guy7cc.cruiseskyblock.util;

import io.github.guy7cc.cruiseskyblock.CruiseSkyblock;

public class TranslationUtil {
    public static String key(String prefix, String suffix) {
        return prefix + "." + CruiseSkyblock.SHORT_NAME + "." + suffix;
    }

    public static String guiKey(String suffix) {
        return key("gui", suffix);
    }
}
