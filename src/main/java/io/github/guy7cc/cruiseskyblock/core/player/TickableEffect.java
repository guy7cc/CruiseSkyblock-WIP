package io.github.guy7cc.cruiseskyblock.core.player;

import io.github.guy7cc.cruiseskyblock.core.system.Tickable;

public class TickableEffect implements SpecialEffect, Tickable {
    private final SpecialEffectProperty property;

    public TickableEffect(SpecialEffectProperty property) {
        this.property = property;
    }

    @Override
    public void tick(int globalTick) {

    }

    @Override
    public SpecialEffectProperty getProperty() {
        return property;
    }
}
