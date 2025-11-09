package io.github.lone64.ether.api.config.impl;

import com.google.gson.JsonObject;
import io.github.lone64.ether.api.config.AbstractConfig;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.List;

@SuppressWarnings("all")
public class YamlConfig extends AbstractConfig {
    public final YamlConfiguration config;

    public YamlConfig(JavaPlugin plugin) {
        super(plugin);
        this.config = YamlConfiguration.loadConfiguration(this.parent);
    }

    @Override
    public boolean save() {
        try {
            this.config.save(this.parent);
            return true;
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not save config file!", e);
        }
    }

    @Override
    public boolean reload() {
        return false;
    }

    @Override
    public boolean exists() {
        return this.parent.exists();
    }

    @Override
    public void remove(String path) {
        this.set(path, null);
    }

    @Override
    public void set(String path, Object value) {
        this.config.set(path, value);
        this.save();
    }

    @Override
    public void add(String path, Object value) {
        if (!contains(path)) set(path, value);
    }

    @Override
    public int getInt(String path) {
        return this.config.getInt(path);
    }

    @Override
    public int getInt(String path, int def) {
        return this.config.getInt(path, def);
    }

    @Override
    public long getLong(String path) {
        return this.config.getLong(path);
    }

    @Override
    public long getLong(String path, long def) {
        return this.config.getLong(path, def);
    }

    @Override
    public float getFloat(String path) {
        return getNumber(path).floatValue();
    }

    @Override
    public float getFloat(String path, float def) {
        return getNumber(path, def).floatValue();
    }

    @Override
    public double getDouble(String path) {
        return getNumber(path).doubleValue();
    }

    @Override
    public double getDouble(String path, double def) {
        return getNumber(path, def).doubleValue();
    }

    @Override
    public Number getNumber(String path) {
        return getInt(path);
    }

    @Override
    public Number getNumber(String path, Number def) {
        return getInt(path, def.intValue());
    }

    @Override
    public boolean getBoolean(String path) {
        return this.config.getBoolean(path);
    }

    @Override
    public boolean getBoolean(String path, boolean def) {
        return this.config.getBoolean(path, def);
    }

    @Override
    public String getString(String path) {
        return this.config.getString(path);
    }

    @Override
    public String getString(String path, String def) {
        return this.config.getString(path, def);
    }

    @Override
    public List<String> getStringList(String path) {
        return this.config.getStringList(path);
    }

    @Override
    public List<String> getStringList(String path, List<String> def) {
        if (!contains(path)) return def;
        return this.config.getStringList(path);
    }

    @Override
    public boolean contains(String path) {
        return this.config.contains(path);
    }

    @Override
    public File getAsouluteFile() {
        return this.parent;
    }

    @Override
    public JsonObject getJsonConfig() {
        throw new IllegalArgumentException("Not yet implemented.");
    }

    @Override
    public YamlConfiguration getYamlConfig() {
        return this.config;
    }
}