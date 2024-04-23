package io.github.guy7cc.cruiseskyblock.core.gui;

import io.github.guy7cc.cruiseskyblock.core.system.Tickable;
import io.github.guy7cc.cruiseskyblock.core.gui.sidebar.Sidebar;
import io.github.guy7cc.cruiseskyblock.core.gui.sidebar.VariableSizedSidebar;
import org.bukkit.entity.Player;

public class PlayerGui implements Tickable {
    public final Sidebar sidebar;

    public PlayerGui(Player player) {
        sidebar = new VariableSizedSidebar(player);
    }

    @Override
    public void tick(int globalTick) {
        sidebar.tick(globalTick);
    }

    public void dispose() {
        sidebar.dispose();
    }
}
