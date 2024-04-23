package io.github.guy7cc.cruiseskyblock.core.gui.sidebar;

import io.github.guy7cc.cruiseskyblock.core.system.Tickable;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.ScoreboardManager;

import java.util.Collection;

public interface Sidebar extends Tickable {
    void setScoreboard(ScoreboardManager manager);

    Player getPlayer();

    void addComponent(SidebarComponent component);

    void addComponent(Collection<SidebarComponent> collection);

    void setComponent(int index, SidebarComponent component);

    void removeComponent(SidebarComponent component);

    void removeComponent(String tag);

    void refreshText(int index, String text);

    void dispose();
}
