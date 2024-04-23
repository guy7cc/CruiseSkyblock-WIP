package io.github.guy7cc.cruiseskyblock.core.player;

import io.github.guy7cc.cruiseskyblock.core.system.ModifierPipeline;
import io.github.guy7cc.cruiseskyblock.core.system.ValueModifier;

import java.util.function.Function;

public class PlayerModifierPipeline<T> extends ModifierPipeline<T> implements SpecialEffectListener {
    public final Class<T> clazz;

    public PlayerModifierPipeline(Function<T, T> cloner, Class<T> clazz){
        super(cloner);
        this.clazz = clazz;
    }

    @Override
    public void addEffect(SpecialEffect effect) {
        if(effect instanceof ModifierEffect<?> modifier && clazz == modifier.clazz){
            add((ValueModifier<T>) effect);
        }
    }

    @Override
    public boolean removeEffect(SpecialEffect effect) {
        if(effect instanceof ModifierEffect<?> modifier && clazz == modifier.clazz){
            return remove((ValueModifier<T>) effect);
        }
        return false;
    }

    @Override
    public void clearEffects() {
        clear();
    }
}
