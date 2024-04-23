package io.github.guy7cc.cruiseskyblock.io;

import io.github.guy7cc.cruiseskyblock.CruiseSkyblock;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class YamlConfigWrapper {
    private String path;
    private File file;
    private FileConfiguration customConfig;

    public YamlConfigWrapper(String path) {
        this.path = path;
        initialize();
    }

    public FileConfiguration getCustomConfig() {
        return this.customConfig;
    }

    private void initialize() {
        file = new File(CruiseSkyblock.plugin.getDataFolder(), path);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            CruiseSkyblock.plugin.saveResource(path, false);
        }
        customConfig = YamlConfiguration.loadConfiguration(file);
        load();
    }

    protected void load() {

    }

    public void save() {
        try {
            customConfig.save(file);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
