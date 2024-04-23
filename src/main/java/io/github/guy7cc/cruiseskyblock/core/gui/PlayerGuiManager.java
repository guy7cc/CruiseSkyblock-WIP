package io.github.guy7cc.cruiseskyblock.core.gui;

import io.github.guy7cc.cruiseskyblock.core.system.Tickable;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.ScoreboardManager;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class PlayerGuiManager implements Tickable {
    private final Map<Player, PlayerGui> map = new HashMap<>();

    public PlayerGuiManager() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            onPlayerJoin(player);
        }
    }

    @Override
    public void tick(int globalTick) {
        for (PlayerGui gui : map.values()) {
            gui.tick(globalTick);
        }
    }

    public void onPlayerJoin(Player player) {
        map.put(player, new PlayerGui(player));
    }

    public void onPlayerQuit(Player player) {
        if (map.containsKey(player)) {
            PlayerGui gui = map.get(player);
            gui.dispose();
            map.remove(player);
        }

    }

    public PlayerGui get(Player player) {
        return map.get(player);
    }

    public void forEach(Consumer<? super PlayerGui> consumer) {
        map.values().forEach(consumer);
    }

    public void setScoreboard(ScoreboardManager manager) {
        for (PlayerGui gui : map.values()) {
            gui.sidebar.setScoreboard(manager);
        }
    }

    public void dispose() {
        for (PlayerGui gui : map.values()) {
            gui.dispose();
        }
    }

    public void reset() {
        dispose();
        for (Player player : Bukkit.getOnlinePlayers()) {
            onPlayerJoin(player);
        }
    }
}
