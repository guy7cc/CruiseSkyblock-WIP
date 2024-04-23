package io.github.guy7cc.cruiseskyblock.core.system;

public class ValueModifier<T> {
    public final ModifierPriority priority;

    public ValueModifier(ModifierPriority priority) {
        this.priority = priority;
    }

    /**
     * @param baseValue A value before being passed through the pipeline. Do not modify this
     * @param passedValue A value that is passed through the pipeline. You can modify this directly
     */
    public T pass(final T baseValue, T passedValue){
        return passedValue;
    }

    public boolean needsCalcEveryTime(){
        return false;
    }
}
