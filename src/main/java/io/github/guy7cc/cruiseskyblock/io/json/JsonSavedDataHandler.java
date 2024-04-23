package io.github.guy7cc.cruiseskyblock.io.json;

import com.google.gson.JsonObject;

import java.util.List;

public interface JsonSavedDataHandler {
    String getDataGroupName();

    void handleData(List<JsonObject> input);

    void saveData(List<JsonObject> output);
}
