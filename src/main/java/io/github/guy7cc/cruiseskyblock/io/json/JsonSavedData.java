package io.github.guy7cc.cruiseskyblock.io.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.github.guy7cc.cruiseskyblock.LoggerWrapper;
import io.github.guy7cc.cruiseskyblock.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonSavedData {
    private final String fileName;
    private final GsonWrapper gson;
    private final LoggerWrapper log;

    private final Map<String, List<JsonObject>> groups = new HashMap<>();

    public JsonSavedData(String fileName, GsonWrapper gson, LoggerWrapper log) {
        this.fileName = fileName;
        this.gson = gson;
        this.log = log;
    }

    public void load() {
        JsonElement root = gson.load(fileName);
        if(root == null) return;
        if (!root.isJsonArray()) {
            log.warn(String.format("%s is not formatted properly as JsonSavedData.", fileName));
            return;
        }
        JsonArray array = root.getAsJsonArray();
        int i = 0;
        for (JsonElement groupElement : array) {
            try {
                JsonObject groupObject = groupElement.getAsJsonObject();
                String groupName = groupObject.get("group").getAsString();
                JsonArray groupObjects = groupObject.get("objects").getAsJsonArray();
                List<JsonObject> list = new ArrayList<>();
                for (JsonElement element : groupObjects) {
                    JsonObject object = element.getAsJsonObject();
                    list.add(object);
                }
                groups.put(groupName, list);
            } catch (Exception exception) {
                log.warn(String.format("Detected an invalid data from %s, which is the %s object.", fileName, StringUtil.ordinal(i)));
                continue;
            }
            ++i;
        }
    }

    public void save(){

    }

    public void supplyData(JsonSavedDataHandler handler) {
        String groupName = handler.getDataGroupName();
        if(groups.containsKey(groupName)) handler.handleData(groups.get(groupName));
        else handler.handleData(List.of());
    }

    public void gatherData(JsonSavedDataHandler handler){
        List<JsonObject> list = new ArrayList<>();
        handler.saveData(list);
        groups.put(handler.getDataGroupName(), list);
    }

    public void clearCache() {
        groups.clear();
    }
}
