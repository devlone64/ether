package io.github.lone64.ether.api.utils.file;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import io.github.lone64.ether.api.config.impl.JsonConfig;
import lombok.experimental.UtilityClass;
import org.bukkit.plugin.Plugin;

import java.io.*;

@UtilityClass
public class FileUtil {
    public boolean saveJson(JsonConfig config, JsonObject src) {
        if (!config.exists() && !config.createNewFile()) return false;
        try (var writer = new FileWriter(config.getAsouluteFile())) {
            var gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(src, writer);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public JsonObject loadJson(File file) {
        try {
            var parent = file.getParentFile();
            if (!parent.exists() && !parent.mkdir()) {
                return new JsonObject();
            } else if (!file.exists() && !file.createNewFile()) {
                return new JsonObject();
            }

            var gson = new Gson();
            try (var reader = new FileReader(file)) {
                var obj = gson.fromJson(reader, JsonObject.class);
                if (obj == null) return new JsonObject();
                return obj;
            } catch (IOException e) {
                return new JsonObject();
            }
        } catch (IOException e) {
            return new JsonObject();
        }
    }

    public JsonObject loadJson(Plugin plugin, String path) {
        var resource = plugin.getResource(path);
        if (resource == null) return new JsonObject();
        var gson = new Gson();
        try (var reader = new InputStreamReader(resource)) {
            var obj = gson.fromJson(reader, JsonObject.class);
            if (obj == null) return new JsonObject();
            return obj;
        } catch (IOException e) {
            return null;
        }
    }
}