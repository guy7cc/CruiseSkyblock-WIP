package io.github.guy7cc.cruiseskyblock.core.player;

import java.util.Set;

public class SpecialEffectSet implements SpecialEffect {
    private final SpecialEffectProperty property;
    public final Set<SpecialEffect> set;

    public SpecialEffectSet(SpecialEffectProperty property, Set<SpecialEffect> set){
        this.property = property;
        this.set = set;
    }

    @Override
    public SpecialEffectProperty getProperty() {
        return property;
    }
}
