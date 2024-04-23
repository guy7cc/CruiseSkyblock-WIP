package io.github.guy7cc.cruiseskyblock.core.system;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

public class ModifierPipeline<T> implements ModifierListener<T> {
    private final Set<ValueModifier<T>>[] modifiers;
    private final Function<T, T> cloner;
    private int needsCalcEveryTime = 0;

    public ModifierPipeline(Function<T, T> cloner){
        modifiers = new Set[ModifierPriority.values().length];
        this.cloner = cloner;
        for(int i = 0; i < modifiers.length; ++i){
            modifiers[i] = new HashSet<>();
        }
    }

    public T pass(T baseValue){
        T value = cloner.apply(baseValue);
        for(Set<ValueModifier<T>> set : modifiers){
            for(ValueModifier<T> eff : set){
                value = eff.pass(baseValue, value);
            }
        }
        return value;
    }

    @Override
    public void add(ValueModifier<T> modifier){
        modifiers[modifier.priority.ordinal()].add(modifier);
        if(modifier.needsCalcEveryTime()) ++needsCalcEveryTime;
    }

    @Override
    public boolean remove(ValueModifier<T> modifier){
        boolean result = modifiers[modifier.priority.ordinal()].remove(modifier);
        if(result && modifier.needsCalcEveryTime()) --needsCalcEveryTime;
        return result;
    }

    @Override
    public void clear(){
        for(Set<ValueModifier<T>> set : modifiers){
            set.clear();
        }
        needsCalcEveryTime = 0;
    }

    /**
     * This means the pipeline returns the same value if the passed base value is fixed.
     */
    public boolean needsCalcEveryTime(){
        return needsCalcEveryTime > 0;
    }
}
