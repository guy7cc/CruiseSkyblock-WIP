package io.github.guy7cc.cruiseskyblock.core.player;

public class MiscEffect implements SpecialEffect {
    private final SpecialEffectProperty property;
    private final MiscEffectType type;

    public MiscEffect(SpecialEffectProperty property, MiscEffectType type){
        this.property = property;
        this.type = type;
    }

    @Override
    public SpecialEffectProperty getProperty() {
        return property;
    }
}
