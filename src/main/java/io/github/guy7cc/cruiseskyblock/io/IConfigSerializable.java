package io.github.guy7cc.cruiseskyblock.io;

import org.bukkit.configuration.ConfigurationSection;

public interface IConfigSerializable {
    void serialize(ConfigurationSection section);

    void deserialize(ConfigurationSection section);
}
