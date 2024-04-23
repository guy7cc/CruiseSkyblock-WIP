package io.github.guy7cc.cruiseskyblock.core.spawner;

import io.github.guy7cc.cruiseskyblock.core.system.RegistryObject;
import io.github.guy7cc.cruiseskyblock.core.handler.spawner.CustomSpawnerEntityEventHandler;
import org.bukkit.entity.Entity;
import org.bukkit.util.BoundingBox;

public abstract class CustomSpawner extends RegistryObject {
    public final int spawnCount;
    public final int minDelay;
    public final int maxDelay;
    public final float detectRange;
    public final float renderRange;
    public final BoundingBox spawnBox;

    public CustomSpawner(int id, String name, int spawnCount, int minDelay, int maxDelay, float detectRange, float renderRange, BoundingBox spawnBox) {
        super(id, name);
        this.spawnCount = spawnCount;
        this.minDelay = minDelay;
        this.maxDelay = maxDelay;
        this.detectRange = detectRange;
        this.renderRange = renderRange;
        this.spawnBox = spawnBox;
    }

    public CustomSpawner(int id, String name, int spawnCount) {
        this(id, name, spawnCount, 100, 400, 10f, 15f, new BoundingBox(-4.5, -1.5, -4.5, 4.5, 1.5, 4.5));
    }

    /**
     * You need to call onSpawn with each spawned entity to put tags on them and to register them to the state
     */
    public abstract void spawn(CustomSpawnerState state, CustomSpawnerEntityEventHandler onSpawn);

    public void tick(CustomSpawnerState state, int tick) {

    }

    public void onEnabled(CustomSpawnerState state) {

    }

    public void onPlayerDetected(CustomSpawnerState state) {

    }

    public void onPlayerOutOfDetection(CustomSpawnerState state) {

    }

    public void onEntityKilled(CustomSpawnerState state, Entity entity) {

    }

    public boolean breakingEffect(CustomSpawnerState state, int tick) {
        return true;
    }

    public void onDisabled(CustomSpawnerState state) {

    }
}
