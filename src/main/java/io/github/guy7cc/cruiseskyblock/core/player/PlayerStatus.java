package io.github.guy7cc.cruiseskyblock.core.player;

import io.github.guy7cc.cruiseskyblock.core.gui.sidebar.Sidebar;
import io.github.guy7cc.cruiseskyblock.core.gui.sidebar.SidebarComponent;
import io.github.guy7cc.cruiseskyblock.core.system.ElementalVector;
import io.github.guy7cc.cruiseskyblock.core.system.ModifiableValueHolder;
import io.github.guy7cc.cruiseskyblock.core.system.ModifierPipeline;
import io.github.guy7cc.cruiseskyblock.core.system.Tickable;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerStatus implements Tickable {
    public final Player player;

    private final SpecialEffect[] effects = new SpecialEffect[41];
    private final Map<SpecialEffectTarget, SpecialEffectListener> map = new HashMap<>();
    private final SpecialEffectTicker tick = new SpecialEffectTicker();
    private final PlayerModifiableValueHolder<Double> maxHealth = new PlayerModifiableValueHolder<>(40D, d -> d, Double.class);
    private final PlayerModifiableValueHolder<Integer> maxMagic = new PlayerModifiableValueHolder<>(100, i -> i, Integer.class);
    private final PlayerModifierPipeline<Double> hatredModifier = new PlayerModifierPipeline<>(d -> d, Double.class);
    private final PlayerModifierPipeline<ElementalVector> armor = new PlayerModifierPipeline<>(v -> (ElementalVector) v.clone(), ElementalVector.class);
    private final PlayerModifierPipeline<Integer> moneyModifier = new PlayerModifierPipeline<>(i -> i, Integer.class);
    private final MiscEffectSet misc = new MiscEffectSet();
    
    private double health = 10;
    private int magic;
    private double hatred;
    private int money;
    private int maxMoney;

    public PlayerStatus(Player player){
        this.player = player;
        map.put(SpecialEffectTarget.TICK, tick);
        map.put(SpecialEffectTarget.MAX_HEALTH, maxHealth);
        map.put(SpecialEffectTarget.MAX_MAGIC, maxMagic);
        map.put(SpecialEffectTarget.HATRED_MODIFIER, hatredModifier);
        map.put(SpecialEffectTarget.ARMOR, armor);
        map.put(SpecialEffectTarget.MONEY_MODIFIER, moneyModifier);
        map.put(SpecialEffectTarget.MISC, misc);
    }

    @Override
    public void tick(int globalTick) {
        tick.tick(globalTick);
        applyHealth();
    }

    public void applyHealth(){
        double scale = health / maxHealth.get();
        int value;
        if(scale < 1 / 40D) value = 1;
        else value = (int)Math.floor(scale);
        player.setHealth(value);
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
        int index = playerInvIndex - 5;
        SpecialEffect effect = effects[index];
        if(effect != null) innerRemove(effect);
        effects[index] = null;
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

    public List<SidebarComponent> getSidebarComponents(){
        String tag = "PlayerStatus";
        return List.of(new SidebarComponent(tag) {
            @Override
            public void tick(int globalTick) {
                setText(String.format("HP: %.1f/%.1f", health, maxHealth.get()));
            }
        }, new SidebarComponent(tag) {
            @Override
            public void tick(int globalTick) {
                setText(String.format("MP: %s/%s", magic, maxMagic.get()));
            }
        }, new SidebarComponent(tag) {
            @Override
            public void tick(int globalTick) {
                setText(String.format("%s G", money));
            }
        });
    }
}
