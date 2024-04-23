package io.github.guy7cc.cruiseskyblock.core.command;

import io.github.guy7cc.cruiseskyblock.CruiseSkyblock;
import io.github.guy7cc.cruiseskyblock.core.gui.PlayerGui;
import io.github.guy7cc.cruiseskyblock.core.gui.sidebar.ProfiledTimerComponentFactory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CsbDebugCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 2 && args[0].equals("startTimer") && sender instanceof Player player) {
            CruiseSkyblock.timer.startTracker(args[1]);
            PlayerGui gui = CruiseSkyblock.playerGui.get(player);
            if (gui != null) {
                gui.sidebar.addComponent(ProfiledTimerComponentFactory.all(args[1]));
            }
            return true;
        } else if (args.length == 1 && args[0].equals("removeTimer")) {
            CruiseSkyblock.timer.clearTracker();
            CruiseSkyblock.playerGui.forEach(gui -> gui.sidebar.removeComponent("ProfiledTimer"));
            return true;
        }
        return false;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) {
            return CommandUtil.getOptions(args[0], List.of("startTimer", "removeTimer"));
        } else if (args.length == 2 && args[0].equals("startTimer")) {
            return CommandUtil.getOptions(args[1], CruiseSkyblock.timer.getProfileSet());
        }
        return null;
    }
}
