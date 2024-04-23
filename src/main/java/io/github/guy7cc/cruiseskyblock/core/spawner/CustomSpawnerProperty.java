package io.github.guy7cc.cruiseskyblock.core.spawner;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import io.github.guy7cc.cruiseskyblock.io.json.ExtendedJsonObjectWrapper;
import io.github.guy7cc.cruiseskyblock.io.json.JsonConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CustomSpawnerProperty extends ExtendedJsonObjectWrapper {
    public CustomSpawnerProperty(CustomSpawnerState state) {
        super();
        addProperty(state.uuid, "uuid");
    }

    public UUID getSpawnerUUID(){
        return getUUID("uuid");
    }

    public void addAccessory(UUID accessoryUUID){
        JsonArray array = getOrCreateArray("accessories");
        array.add(accessoryUUID.toString());
    }

    public List<UUID> getAccessories(){
        JsonArray array = getOrCreateArray("accessories");
        List<UUID> list = new ArrayList<>();
        for(JsonElement element : array){
            UUID accessoryUUID = JsonConverter.toUUID(element);
            if(accessoryUUID != null) list.add(accessoryUUID);
        }
        return list;
    }

    public void clearAccessory(){
        addArray(new JsonArray(), "accessories");
    }
}
