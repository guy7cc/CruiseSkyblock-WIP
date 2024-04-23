package io.github.guy7cc.cruiseskyblock.io.json;

import com.google.gson.*;
import io.github.guy7cc.cruiseskyblock.CruiseSkyblock;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.UUID;

public class GsonWrapper {
    public Gson gson;

    public GsonWrapper() {
        gson = new GsonBuilder()
                .registerTypeAdapter(UUID.class, new UuidAdapter())
                .create();
    }

    public JsonElement toJson(Object serializable) {
        return gson.toJsonTree(serializable);
    }

    public <T> T fromJson(JsonElement json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    public void save(@NotNull JsonElement element, @NotNull String fileName) {
        File file = new File(CruiseSkyblock.plugin.getDataFolder(), fileName);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            writer.write(element.toString());
        } catch (IOException exception) {
            CruiseSkyblock.log.exception(String.format("%s could not be saved due to I/O errors.", fileName), exception);
        } catch (SecurityException exception) {
            CruiseSkyblock.log.exception(String.format("%s could not be saved due to security problems.", fileName), exception);
        }
    }

    @Nullable
    public JsonElement load(@NotNull String fileName) {
        File file = new File(CruiseSkyblock.plugin.getDataFolder(), fileName);
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return JsonParser.parseString(sb.toString());
        } catch (FileNotFoundException exception) {
            return null;
        } catch (IOException exception) {
            CruiseSkyblock.log.exception(String.format("%s could not be read due to I/O errors.", fileName), exception);
        } catch (JsonParseException exception) {
            CruiseSkyblock.log.exception(String.format("%s could not be read because the file format was invalid.", fileName), exception);
        }
        return null;
    }
}
