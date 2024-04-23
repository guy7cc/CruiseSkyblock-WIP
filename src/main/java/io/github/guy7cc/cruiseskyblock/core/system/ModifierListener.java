package io.github.guy7cc.cruiseskyblock.core.system;

public interface ModifierListener<T> {
    void add(ValueModifier<T> modifier);

    boolean remove(ValueModifier<T> modifier);

    void clear();
}
