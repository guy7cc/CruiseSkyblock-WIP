package io.github.guy7cc.cruiseskyblock.core.player;

import io.github.guy7cc.cruiseskyblock.core.system.ModifierPriority;
import io.github.guy7cc.cruiseskyblock.core.system.ValueModifier;

public class ModifierEffect<T> extends ValueModifier<T> implements SpecialEffect {
    private final SpecialEffectProperty property;
    public final Class<T> clazz;

    public ModifierEffect(SpecialEffectProperty property, ModifierPriority priority, Class<T> clazz) {
        super(priority);
        this.property = property;
        this.clazz = clazz;
    }

    @Override
    public SpecialEffectProperty getProperty() {
        return property;
    }
}
