package io.github.guy7cc.cruiseskyblock.core.spawner;

import io.github.guy7cc.cruiseskyblock.CruiseSkyblock;
import io.github.guy7cc.cruiseskyblock.core.system.BaseSelectiveTicker;
import io.github.guy7cc.cruiseskyblock.core.system.ConditionalTickable;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CustomSpawnerTicker extends BaseSelectiveTicker<CustomSpawnerState> {
    private final Map<UUID, CustomSpawnerState> stateMap = new HashMap<>();
    private int processIndex = 0;

    public CustomSpawnerTicker(int size) {
        super(size);
    }

    public CustomSpawnerState getState(UUID uuid) {
        return stateMap.get(uuid);
    }

    @Override
    public void add(@NotNull CustomSpawnerState state) {
        stateMap.put(state.uuid, state);
        super.add(state);
    }

    @Override
    public void remove(CustomSpawnerState state) {
        stateMap.remove(state.uuid);
        super.remove(state);
    }

    @Override
    public void tick(int globalTick) {
        CruiseSkyblock.timer.push("SpawnProcess");
        if (inTick[processIndex] != null) ((CustomSpawnerState) inTick[processIndex]).processSpawn(globalTick);
        processIndex = (processIndex + 1) % inTick.length;
        CruiseSkyblock.timer.popPush("Tick");
        super.tick(globalTick);
        CruiseSkyblock.timer.pop();
    }

    protected void queueToIndex(int index) {
        inTick[index] = queue.poll();
        ((CustomSpawnerState) inTick[index]).onEnabled();
        vacantCount--;
    }

    protected void indexToQueue(int index) {
        ((CustomSpawnerState) inTick[index]).onDisabled();
        queue.add(inTick[index]);
        inTick[index] = null;
        vacantCount++;
    }

    protected void indexToNone(int index) {
        ((CustomSpawnerState) inTick[index]).onDisabled();
        inTick[index] = null;
        vacantCount++;
    }

    public void dispose() {
        for (ConditionalTickable tickable : inTick) {
            if (tickable != null) ((CustomSpawnerState) tickable).onDisabled();
        }
    }
}
