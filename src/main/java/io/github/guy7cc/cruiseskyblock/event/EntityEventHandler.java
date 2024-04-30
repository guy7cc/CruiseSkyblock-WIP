package io.github.guy7cc.cruiseskyblock.event;

import io.github.guy7cc.cruiseskyblock.core.entity.CustomEntityProperty;
import io.github.guy7cc.cruiseskyblock.core.entity.CustomEntityPropertyManager;
import io.github.guy7cc.cruiseskyblock.core.spawner.CustomSpawnerState;
import io.github.guy7cc.cruiseskyblock.core.spawner.CustomSpawnerTicker;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRemoveEvent;
import org.bukkit.event.entity.EntitySpawnEvent;

import java.util.UUID;

public class EntityEventHandler implements Listener {
    private final CustomEntityPropertyManager propertyManager;
    private final CustomSpawnerTicker spawnerTicker;

    public EntityEventHandler(CustomEntityPropertyManager propertyManager, CustomSpawnerTicker spawnerTicker) {
        this.propertyManager = propertyManager;
        this.spawnerTicker = spawnerTicker;
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event){

    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {

    }

    @EventHandler
    public void onEntityLoad(EntitySpawnEvent event) {

    }

    @EventHandler
    public void onEntityRemove(EntityRemoveEvent event) {
        if (propertyManager.hasProperty(event.getEntity())) {
            if (event.getCause() == EntityRemoveEvent.Cause.DEATH) {
                CustomEntityProperty property = propertyManager.get(event.getEntity());
                UUID spawnerUUID = property.getSpawnerUUID();
                if (spawnerUUID != null) {
                    CustomSpawnerState state = spawnerTicker.getState(spawnerUUID);
                    if (state != null) state.onEntityKilled(event.getEntity());
                }
            } else if (event.getCause() == EntityRemoveEvent.Cause.UNLOAD) {
                if (propertyManager.shouldEntityBeRemovedWhenUnloadAndDisabled(event.getEntity())) {
                    event.getEntity().remove();
                }
            }
            propertyManager.remove(event.getEntity());
        }
    }


}
