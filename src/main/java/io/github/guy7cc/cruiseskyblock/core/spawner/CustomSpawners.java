package io.github.guy7cc.cruiseskyblock.core.spawner;

import io.github.guy7cc.cruiseskyblock.core.system.Registry;

public class CustomSpawners {
    public static final Registry<CustomSpawner> REGISTRY = new Registry<>("spawner");

    public static final CustomSpawner EXAMPLE_SPAWNER = REGISTRY.register(new ExampleSpawner(0));
}
