package io.github.guy7cc.cruiseskyblock.core.player;

public interface SpecialEffectListener {
    void addEffect(SpecialEffect effect);

    boolean removeEffect(SpecialEffect effect);

    void clearEffects();
}
