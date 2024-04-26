package io.github.guy7cc.cruiseskyblock.core.system;

import java.util.function.Function;

public class ModifiableValueHolder<T> implements ModifierListener<T> {
    private T baseValue;
    private T calculatedValue;
    public final ModifierPipeline<T> pipeline;

    public ModifiableValueHolder(T baseValue, Function<T, T> cloner){
        this.baseValue = baseValue;
        pipeline = new ModifierPipeline<>(cloner);
    }

    public void setBaseValue(T baseValue){
        this.baseValue = baseValue;
    }

    public T get(){
        if(pipeline.needsCalcEveryTime()){
            calculatedValue = pipeline.pass(baseValue);
        }
        return calculatedValue;
    }

    @Override
    public void add(ValueModifier<T> modifier) {
        pipeline.add(modifier);
        if(!pipeline.needsCalcEveryTime()) calculatedValue = pipeline.pass(baseValue);
    }

    @Override
    public boolean remove(ValueModifier<T> modifier) {
        boolean result = pipeline.remove(modifier);
        if(result && !pipeline.needsCalcEveryTime()) calculatedValue = pipeline.pass(baseValue);
        return result;
    }

    @Override
    public void clear() {
        pipeline.clear();
    }
}
