package io.github.guy7cc.cruiseskyblock.core.player;

import io.github.guy7cc.cruiseskyblock.core.system.Tickable;

import java.util.HashSet;
import java.util.Set;

public class SpecialEffectTicker implements SpecialEffectListener, Tickable {
    private final Set<TickableEffect> tickables = new HashSet<>();

    @Override
    public void tick(int globalTick) {
        for(TickableEffect tickable : tickables){
            tickable.tick(globalTick);
        }
    }

    @Override
    public void addEffect(SpecialEffect effect) {
        if(effect instanceof TickableEffect tickable){
            tickables.add(tickable);
        }
    }

    @Override
    public boolean removeEffect(SpecialEffect effect) {
        return tickables.remove(effect);
    }

    @Override
    public void clearEffects() {
        tickables.clear();
    }
}
