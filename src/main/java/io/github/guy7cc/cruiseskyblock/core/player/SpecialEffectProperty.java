package io.github.guy7cc.cruiseskyblock.core.player;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SpecialEffectProperty {
    public final Player player;
    public final int inventoryIndex;
    public final ItemStack itemStack;
    public final SpecialEffectTarget target;

    public SpecialEffectProperty(Player player, int inventoryIndex, ItemStack itemStack, SpecialEffectTarget target){
        this.player = player;
        this.inventoryIndex = inventoryIndex;
        this.itemStack = itemStack;
        this.target = target;
    }
}
