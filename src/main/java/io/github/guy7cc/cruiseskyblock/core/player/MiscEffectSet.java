package io.github.guy7cc.cruiseskyblock.core.player;

import java.util.HashSet;
import java.util.Set;

public class MiscEffectSet implements SpecialEffectListener {
    private final Set<MiscEffect> set = new HashSet<>();

    @Override
    public void addEffect(SpecialEffect effect) {
        if(effect instanceof MiscEffect misc){
            set.add(misc);
        }
    }

    @Override
    public boolean removeEffect(SpecialEffect effect) {
        return set.remove(effect);
    }

    @Override
    public void clearEffects() {
        set.clear();
    }
}
