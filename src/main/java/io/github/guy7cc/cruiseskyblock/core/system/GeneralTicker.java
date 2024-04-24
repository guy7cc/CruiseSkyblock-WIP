package io.github.guy7cc.cruiseskyblock.core.system;

import io.github.guy7cc.cruiseskyblock.CruiseSkyblock;
import io.github.guy7cc.cruiseskyblock.ProfiledTimer;
import io.github.guy7cc.cruiseskyblock.core.spawner.CustomSpawnerState;
import io.github.guy7cc.cruiseskyblock.core.spawner.CustomSpawners;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.List;

public class GeneralTicker extends BukkitRunnable {
    private int globalTick = 0;

    private final List<Tickable> tickables;

    public final ProfiledTimer timer;

    public GeneralTicker(ProfiledTimer timer, Tickable... tickables) {
        this.timer = timer;
        this.tickables = Arrays.stream(tickables).toList();
    }

    @Override
    public void run() {
        ++globalTick;
        for (Tickable tickable : tickables) {
            timer.push(tickable.getClass().getSimpleName());
            tickable.tick(globalTick);
            timer.pop();
        }
        if (globalTick == 100) {
            CruiseSkyblock.customSpawner.add(new CustomSpawnerState(CustomSpawners.EXAMPLE_SPAWNER, new Location(CruiseSkyblock.plugin.getServer().getWorlds().get(0), 526, 64, -1926)));
        }
    }
}
