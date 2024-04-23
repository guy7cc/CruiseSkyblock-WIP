package io.github.guy7cc.cruiseskyblock.core.handler.spawner;

import io.github.guy7cc.cruiseskyblock.core.spawner.CustomSpawnerState;
import org.bukkit.entity.Entity;

@FunctionalInterface
public interface CustomSpawnerEntityEventHandler {
    void accept(CustomSpawnerState state, Entity entity);
}
