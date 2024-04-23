package io.github.guy7cc.cruiseskyblock.core.player;

import io.github.guy7cc.cruiseskyblock.core.system.ModifiableValueHolder;
import io.github.guy7cc.cruiseskyblock.core.system.ValueModifier;

import java.util.function.Function;

public class PlayerModifiableValueHolder<T> extends ModifiableValueHolder<T> implements SpecialEffectListener {
    public final Class<T> clazz;

    public PlayerModifiableValueHolder(T baseValue, Function<T, T> cloner, Class<T> clazz) {
        super(baseValue, cloner);
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
