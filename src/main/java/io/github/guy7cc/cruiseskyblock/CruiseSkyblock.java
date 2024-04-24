package io.github.guy7cc.cruiseskyblock;

import com.google.gson.JsonObject;
import io.github.guy7cc.cruiseskyblock.core.command.CsbCommand;
import io.github.guy7cc.cruiseskyblock.core.command.CsbDebugCommand;
import io.github.guy7cc.cruiseskyblock.core.entity.CustomEntityPropertyManager;
import io.github.guy7cc.cruiseskyblock.core.gui.PlayerGuiManager;
import io.github.guy7cc.cruiseskyblock.core.gui.SelectInventoryManager;
import io.github.guy7cc.cruiseskyblock.core.item.CooldownManager;
import io.github.guy7cc.cruiseskyblock.core.item.PlayerItemTicker;
import io.github.guy7cc.cruiseskyblock.core.spawner.CustomSpawnerTicker;
import io.github.guy7cc.cruiseskyblock.core.system.GeneralTicker;
import io.github.guy7cc.cruiseskyblock.event.EntityEventHandler;
import io.github.guy7cc.cruiseskyblock.event.InventoryEventHandler;
import io.github.guy7cc.cruiseskyblock.event.PlayerEventHandler;
import io.github.guy7cc.cruiseskyblock.io.ConfigManager;
import io.github.guy7cc.cruiseskyblock.io.json.GsonWrapper;
import io.github.guy7cc.cruiseskyblock.io.json.JsonSavedData;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.ScoreboardManager;

import java.util.UUID;

public final class CruiseSkyblock extends JavaPlugin {
    public static final String PLUGIN_NAME = "CruiseSkyblock";
    public static final String SHORT_NAME = "csb";
    public static final String NAMESPACE = "cruise_skyblock";

    public static final boolean ON_RELEASE = false;

    public static CruiseSkyblock plugin;

    public static GeneralTicker ticker;

    public static ScoreboardManager scoreboardManager;
    public static PlayerItemTicker playerItem;
    public static PlayerGuiManager playerGui;
    public static CooldownManager cooldown;
    public static SelectInventoryManager selectInventory;

    public static CustomEntityPropertyManager customEntityProperty;
    public static CustomSpawnerTicker customSpawner;

    public static JsonSavedData data;
    public static ConfigManager config;
    public static GsonWrapper gson;

    public static LoggerWrapper log;
    public static ProfiledTimer timer;

    @Override
    public void onEnable() {
        plugin = this;

        gson = new GsonWrapper();

        setupCommand("csb", new CsbCommand());
        if (!ON_RELEASE) setupCommand("csbdb", new CsbDebugCommand());

        log = new LoggerWrapper(getLogger());
        timer = new ProfiledTimer();

        playerItem = new PlayerItemTicker();
        playerGui = new PlayerGuiManager();
        cooldown = new CooldownManager();
        customEntityProperty = new CustomEntityPropertyManager();
        customSpawner = new CustomSpawnerTicker(16);
        selectInventory = new SelectInventoryManager();

        JsonObject object = new JsonObject();
        object.add("uuid", gson.toJson(UUID.randomUUID()));
        gson.save(object, "uuidTest.json");

        data = new JsonSavedData("save.json", gson, log);
        data.load();
        data.supplyData(customEntityProperty);

        data.clearCache();

        config = new ConfigManager();

        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new EntityEventHandler(customEntityProperty, customSpawner), this);
        pluginManager.registerEvents(new PlayerEventHandler(playerGui, cooldown), this);
        pluginManager.registerEvents(new InventoryEventHandler(), this);

        Bukkit.getScheduler().runTask(this, () -> {
            // Runs on the first server tick
            scoreboardManager = Bukkit.getScoreboardManager();
            playerGui.setScoreboard(scoreboardManager);
        });
        ticker = new GeneralTicker(timer, playerItem, playerGui, cooldown, customSpawner);
        ticker.runTaskTimer(plugin, 0, 1);
    }

    @Override
    public void onDisable() {
        playerGui.dispose();
        customEntityProperty.removeEntities();
        customSpawner.dispose();
        data.gatherData(customEntityProperty);
        data.save();
        config.save();
    }

    public <T extends CommandExecutor & TabCompleter>void setupCommand(String name, T executor) {
        PluginCommand command = getCommand(name);
        if (command == null) return;
        command.setExecutor(executor);
        command.setTabCompleter(executor);
    }
}
