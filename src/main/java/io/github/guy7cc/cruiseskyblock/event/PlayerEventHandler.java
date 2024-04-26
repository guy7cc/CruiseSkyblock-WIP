package io.github.guy7cc.cruiseskyblock.event;

import io.github.guy7cc.cruiseskyblock.core.gui.PlayerGuiManager;
import io.github.guy7cc.cruiseskyblock.core.item.CooldownManager;
import io.github.guy7cc.cruiseskyblock.core.item.CustomItem;
import io.github.guy7cc.cruiseskyblock.core.item.CustomItemInteractionResult;
import io.github.guy7cc.cruiseskyblock.core.item.CustomItems;
import io.github.guy7cc.cruiseskyblock.core.player.PlayerStatusManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class PlayerEventHandler implements Listener {
    private final PlayerStatusManager playerStatus;
    private final PlayerGuiManager playerGui;
    private final CooldownManager cooldown;

    public PlayerEventHandler(PlayerStatusManager playerStatus, PlayerGuiManager playerGui, CooldownManager cooldown) {
        this.playerStatus = playerStatus;
        this.playerGui = playerGui;
        this.cooldown = cooldown;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        playerStatus.onPlayerJoin(event.getPlayer());
        playerGui.onPlayerJoin(event.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        playerStatus.onPlayerQuit(event.getPlayer());
        playerGui.onPlayerQuit(event.getPlayer());
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getHand() != EquipmentSlot.HAND) return;
        if (event.getPlayer().getOpenInventory().getType() == InventoryType.CRAFTING) return;

        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ItemStack itemStack = event.getPlayer().getInventory().getItem(slot);
            CustomItem item = CustomItems.byItemStack(itemStack);
            if (item == null) return;

            CustomItemInteractionResult result = item.use(itemStack, event.getPlayer(), slot, event.getAction());
            if (result.shouldShowMessage) event.getPlayer().spigot().sendMessage(result.message);
            if (result != CustomItemInteractionResult.UNAVAILABLE) event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        if (event.getHand() != EquipmentSlot.HAND) return;

        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ItemStack itemStack = event.getPlayer().getInventory().getItem(slot);
            CustomItem item = CustomItems.byItemStack(itemStack);
            if (item == null) return;

            CustomItemInteractionResult result = item.useOnEntity(itemStack, event.getPlayer(), slot, event.getRightClicked());
            if (result.shouldShowMessage) event.getPlayer().spigot().sendMessage(result.message);
            if (result != CustomItemInteractionResult.UNAVAILABLE) event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerItemHeld(PlayerItemHeldEvent event) {
        CustomItem item = CustomItems.byItemStack(event.getPlayer().getInventory().getItem(event.getNewSlot()));
        if (item == null) cooldown.sendEmptyCooldownMessage(event.getPlayer());
        else cooldown.sendCooldownMessage(event.getPlayer(), item);
    }
}
