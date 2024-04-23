package io.github.guy7cc.cruiseskyblock.core.entity;

import com.google.gson.JsonObject;
import io.github.guy7cc.cruiseskyblock.core.spawner.CustomSpawnerState;
import io.github.guy7cc.cruiseskyblock.io.json.ExtendedJsonObjectWrapper;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class CustomEntityProperty extends ExtendedJsonObjectWrapper {
    public CustomEntityProperty(Entity entity){
        super();
        addProperty(entity.getUniqueId(), "uuid");
    }

    private CustomEntityProperty(JsonObject object){
        super(object);
    }

    /**
     * @return The property or null if the passed object does not have an entity uuid
     */
    @Nullable
    public static CustomEntityProperty fromJsonObject(JsonObject object){
        CustomEntityProperty property = new CustomEntityProperty(object);
        if(property.getUUID("uuid") != null) return property;
        return null;
    }

    public UUID getEntityUUID(){
        return getUUID("uuid");
    }

    public boolean persistenceRequired(){
        return getBoolean("persistenceRequired");
    }

    public UUID getSpawnerUUID(){
        return getUUID("spawnerUUID");
    }

    public void setSpawnerUUID(CustomSpawnerState state){
        addProperty(state.uuid, "spawnerUUID");
    }
}
