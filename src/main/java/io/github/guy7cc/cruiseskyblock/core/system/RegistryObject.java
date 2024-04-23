package io.github.guy7cc.cruiseskyblock.core.system;

import io.github.guy7cc.cruiseskyblock.util.TranslationUtil;

public class RegistryObject {
    public final int id;
    public final String name;
    private String translationKey;

    public RegistryObject(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getTranslationKey() {
        return translationKey;
    }

    protected void setTranslationKey(String prefix) {
        translationKey = TranslationUtil.key(prefix, name);
    }
}
