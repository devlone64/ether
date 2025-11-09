package io.github.lone64.ether.api.config.impl;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import io.github.lone64.ether.api.config.AbstractConfig;
import io.github.lone64.ether.api.utils.file.FileUtil;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("all")
public class JsonConfig extends AbstractConfig {
    public JsonObject config;

    public JsonConfig(JavaPlugin plugin) {
        super(plugin);
        this.config = plugin.getResource(this.path) != null ? FileUtil.loadJson(plugin, this.path) : FileUtil.loadJson(this.parent);
    }

    @Override
    public boolean save() {
        return FileUtil.saveJson(this, this.config);
    }

    @Override
    public boolean reload() {
        if (!exists()) return false;
        this.config = this.plugin.getResource(this.path) != null ? FileUtil.loadJson(this.plugin, this.path) : FileUtil.loadJson(this.parent);
        return true;
    }

    @Override
    public boolean exists() {
        return this.parent.exists();
    }

    @Override
    public void remove(String path) {
        JsonObject current = this.config;
        String[] keys = path.split("\\.");
        for (int i = 0; i < keys.length - 1; i++) {
            var key = keys[i];
            if (!current.has(key) || !current.get(key).isJsonObject()) {
                var newObj = new JsonObject();
                current.add(key, newObj);
                current = newObj;
            } else {
                current = current.getAsJsonObject(key);
            }
        }
        var lastKey = keys[keys.length - 1];
        current.remove(lastKey);
        this.save();
    }

    @Override
    public void set(String path, Object value) {
        JsonObject current = this.config;
        String[] keys = path.split("\\.");
        for (int i = 0; i < keys.length - 1; i++) {
            var key = keys[i];
            if (!current.has(key) || !current.get(key).isJsonObject()) {
                var newObj = new JsonObject();
                current.add(key, newObj);
                current = newObj;
            } else {
                current = current.getAsJsonObject(key);
            }
        }
        var lastKey = keys[keys.length - 1];
        current.add(lastKey, new Gson().toJsonTree(value));
        this.save();
    }

    @Override
    public void add(String path, Object value) {
        if (!this.contains(path)) this.set(path, value);
    }

    @Override
    public int getInt(String path) {
        final JsonElement element = getJsonElement(path);
        if (element == null) return 0;
        return element.getAsInt();
    }

    @Override
    public int getInt(String path, int def) {
        if (!contains(path)) return def;
        return getInt(path);
    }

    @Override
    public long getLong(String path) {
        final JsonElement element = getJsonElement(path);
        if (element == null) return 0L;
        return element.getAsLong();
    }

    @Override
    public long getLong(String path, long def) {
        if (!contains(path)) return def;
        return getLong(path);
    }

    @Override
    public float getFloat(String path) {
        final JsonElement element = getJsonElement(path);
        if (element == null) return 0F;
        return element.getAsFloat();
    }

    @Override
    public float getFloat(String path, float def) {
        if (!contains(path)) return def;
        return getFloat(path);
    }

    @Override
    public double getDouble(String path) {
        final JsonElement element = getJsonElement(path);
        if (element == null) return 0D;
        return element.getAsDouble();
    }

    @Override
    public double getDouble(String path, double def) {
        if (!contains(path)) return def;
        return getDouble(path);
    }

    @Override
    public Number getNumber(String path) {
        final JsonElement element = getJsonElement(path);
        if (element == null) return 0;
        return element.getAsNumber();
    }

    @Override
    public Number getNumber(String path, Number def) {
        if (!contains(path)) return def;
        return getNumber(path);
    }

    @Override
    public boolean getBoolean(String path) {
        final JsonElement element = getJsonElement(path);
        if (element == null) return false;
        return element.getAsBoolean();
    }

    @Override
    public boolean getBoolean(String path, boolean def) {
        if (!contains(path)) return def;
        return getBoolean(path);
    }

    @Override
    public String getString(String path) {
        final JsonElement element = getJsonElement(path);
        if (element == null) return null;
        return element.getAsString();
    }

    @Override
    public String getString(String path, String def) {
        if (!contains(path)) return def;
        return getString(path);
    }

    @Override
    public List<String> getStringList(String path) {
        final JsonElement element = getJsonElement(path);
        if (element == null) return new ArrayList<>();
        return element.getAsJsonArray().asList().stream().map(JsonElement::getAsString).toList();
    }

    @Override
    public List<String> getStringList(String path, List<String> def) {
        if (!contains(path)) return def;
        return getStringList(path);
    }

    @Override
    public boolean contains(String path) {
        var current = this.config;
        var keys = path.split("\\.");
        for (int i = 0; i < keys.length; i++) {
            var key = keys[i];
            var element = current.get(key);
            if (element == null) return false;
            if (i == keys.length - 1) return true;
            if (!element.isJsonObject()) return false;
            current = element.getAsJsonObject();
        }
        return false;
    }

    @Override
    public File getAsouluteFile() {
        return this.parent;
    }

    @Override
    public JsonObject getJsonConfig() {
        return this.config;
    }

    @Override
    public YamlConfiguration getYamlConfig() {
        throw new IllegalArgumentException("Not yet implemented.");
    }

    private JsonElement getJsonElement(String path) {
        var current = this.config;
        var keys = path.split("\\.");
        for (int i = 0; i < keys.length; i++) {
            var key = keys[i];
            if (i == keys.length - 1) {
                return current.get(key);
            }
            var element = current.get(key);
            if (!(element instanceof JsonObject))
                return null;
            current = element.getAsJsonObject();
        }
        return null;
    }
}