package io.github.guy7cc.cruiseskyblock.io;

import io.github.guy7cc.cruiseskyblock.core.quest.Quest;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;

public class PlayerLastTimeData implements IConfigSerializable {
    public OfflinePlayer player;
    public Quest lastQuest;

    @Override
    public void serialize(ConfigurationSection section) {
        section.set("UUID", player.getUniqueId().toString());
        section.set("LastQuest", lastQuest.id);
    }

    @Override
    public void deserialize(ConfigurationSection section) {
        player = Bukkit.getOfflinePlayer(section.getString("UUID"));
    }
}
