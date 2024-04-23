package io.github.guy7cc.cruiseskyblock.core.handler.spawner;

import io.github.guy7cc.cruiseskyblock.core.spawner.CustomSpawnerState;

@FunctionalInterface
public interface CustomSpawnerEventHandler {
    void accept(CustomSpawnerState state);
}
