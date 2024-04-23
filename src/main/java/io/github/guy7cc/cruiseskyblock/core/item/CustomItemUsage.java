package io.github.guy7cc.cruiseskyblock.core.item;

import java.util.Locale;

public enum CustomItemUsage {
    NONE,
    LEFT_CLICK,
    RIGHT_CLICK,
    LEFT_CLICK_ON_ENTITY,
    RIGHT_CLICK_ON_ENTITY;

    public String getKey() {
        return toString().toLowerCase(Locale.ROOT);
    }
}
