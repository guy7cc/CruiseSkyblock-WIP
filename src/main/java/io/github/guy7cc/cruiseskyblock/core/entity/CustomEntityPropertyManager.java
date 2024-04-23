package io.github.guy7cc.cruiseskyblock.core.entity;

import com.google.gson.JsonObject;
import io.github.guy7cc.cruiseskyblock.CruiseSkyblock;
import io.github.guy7cc.cruiseskyblock.io.json.JsonSavedDataHandler;
import org.bukkit.Server;
import org.bukkit.entity.Entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CustomEntityPropertyManager implements JsonSavedDataHandler {
    private final Map<UUID, CustomEntityProperty> map = new HashMap<>();

    public void set(Entity entity, CustomEntityProperty property) {
        map.put(entity.getUniqueId(), property);
    }

    public CustomEntityProperty get(Entity entity) {
        return map.get(entity.getUniqueId());
    }

    public boolean hasProperty(Entity entity) {
        return map.containsKey(entity.getUniqueId());
    }

    public void remove(Entity entity) {
        map.remove(entity.getUniqueId());
    }

    public boolean shouldEntityBeRemovedWhenUnloadAndDisabled(Entity entity) {
        return map.containsKey(entity.getUniqueId()) && map.get(entity.getUniqueId()).persistenceRequired();
    }

    public void removeEntities() {
        Server server = CruiseSkyblock.plugin.getServer();
        for (Map.Entry<UUID, CustomEntityProperty> entry : map.entrySet()) {
            Entity entity = server.getEntity(entry.getKey());
            if (entity != null && shouldEntityBeRemovedWhenUnloadAndDisabled(entity)) {
                entity.remove();
            }
        }
    }

    @Override
    public String getDataGroupName() {
        return "entity";
    }

    @Override
    public void handleData(List<JsonObject> input) {
        for (JsonObject object : input) {
            CustomEntityProperty property = CustomEntityProperty.fromJsonObject(object);
            if(property != null) map.put(property.getEntityUUID(), property);
        }
    }

    @Override
    public void saveData(List<JsonObject> output) {
        for (CustomEntityProperty property : map.values()) {
            if (property.persistenceRequired()) output.add(property.object);
        }
    }
}
