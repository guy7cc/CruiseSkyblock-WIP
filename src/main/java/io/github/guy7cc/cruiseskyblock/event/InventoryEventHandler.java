package io.github.guy7cc.cruiseskyblock.event;

import io.github.guy7cc.cruiseskyblock.CruiseSkyblock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryEventHandler implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        CruiseSkyblock.selectInventory.onInventoryClick(event);
    }
}
