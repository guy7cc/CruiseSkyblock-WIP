package io.github.guy7cc.cruiseskyblock.core.player;

import io.github.guy7cc.cruiseskyblock.core.system.ElementalVector;
import io.github.guy7cc.cruiseskyblock.core.system.ModifiableValueHolder;
import io.github.guy7cc.cruiseskyblock.core.system.ModifierPipeline;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class PlayerStatus {
    private final SpecialEffect[] effects = new SpecialEffect[41];
    private final Map<SpecialEffectTarget, SpecialEffectListener> map = new HashMap<>();
    public final SpecialEffectTicker tick = new SpecialEffectTicker();
    public final PlayerModifiableValueHolder<Double> maxHealth = new PlayerModifiableValueHolder<>(40D, d -> d, Double.class);
    public final PlayerModifiableValueHolder<Integer> maxMagic = new PlayerModifiableValueHolder<>(100, i -> i, Integer.class);
    public final PlayerModifierPipeline<Double> hatredModifier = new PlayerModifierPipeline<>(d -> d, Double.class);
    public final PlayerModifierPipeline<ElementalVector> armor = new PlayerModifierPipeline<>(v -> (ElementalVector) v.clone(), ElementalVector.class);

    private double health;
    private int magic;
    private double hatred;

    public PlayerStatus(){
        map.put(SpecialEffectTarget.TICK, tick);
        map.put(SpecialEffectTarget.MAX_HEALTH, maxHealth);
        map.put(SpecialEffectTarget.MAX_MAGIC, maxMagic);
        map.put(SpecialEffectTarget.HATRED_MODIFIER, hatredModifier);
        map.put(SpecialEffectTarget.ARMOR, armor);
    }

    public void damage(ElementalVector damage){
        ElementalVector passedDamage = armor.pass(damage);
        health -= passedDamage.sum();
    }

    public void addSpecialEffect(@NotNull SpecialEffect effect) {
        SpecialEffectProperty property = effect.getProperty();
        if(property.inventoryIndex < 5 || property.inventoryIndex > 45) return;
        int index = property.inventoryIndex - 5;
        if(effects[index] != null){
            removeSpecialEffect(property.inventoryIndex);
        }
        effects[index] = effect;
        innerAdd(effect);
    }

    public void removeSpecialEffect(int playerInvIndex) {
        if(playerInvIndex < 5 || playerInvIndex > 45) return;
        SpecialEffect effect = effects[playerInvIndex - 5];
        if(effect != null) innerRemove(effect);
    }

    private void innerAdd(SpecialEffect effect){
        if(effect instanceof SpecialEffectSet set) {
            for(SpecialEffect child : set.set){
                innerAdd(child);
            }
        } else {
            SpecialEffectListener listener = map.get(effect.getProperty().target);
            if(listener != null) listener.addEffect(effect);
        }
    }

    private void innerRemove(SpecialEffect effect){
        if(effect instanceof SpecialEffectSet set) {
            for(SpecialEffect child : set.set){
                innerRemove(child);
            }
        } else {
            SpecialEffectListener listener = map.get(effect.getProperty().target);
            if(listener != null) listener.removeEffect(effect);
        }
    }
}
