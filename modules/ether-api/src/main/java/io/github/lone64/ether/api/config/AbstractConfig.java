package io.github.lone64.ether.api.config;

import com.google.gson.JsonObject;
import io.github.lone64.ether.api.utils.Utils;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.List;

public abstract class AbstractConfig implements Utils {
    public final String name;
    public final String path;
    public final File parent;
    public final Config.Type type;
    public final JavaPlugin plugin;

    public AbstractConfig(JavaPlugin plugin) {
        this.plugin = plugin;

        final Config config = this.getClass().getAnnotation(Config.class);
        if (this.getClass().isAnnotationPresent(Config.class)) {
            this.name = config.name();
            this.path = config.path();
            this.type = config.type();
            this.parent = new File(plugin.getDataFolder(), this.path + "/" + this.name + "." + config.type().extension);
        } else {
            throw new IllegalArgumentException("@Config annotation is required");
        }
    }

    public boolean createNewFile() {
        try {
            if (this.plugin.getResource(this.path) == null) {
                var data = this.plugin.getDataFolder();
                if (!data.exists() && !data.mkdir()) return false;
                var parent = this.parent.getParentFile();
                if (!parent.exists() && !parent.mkdir()) return false;
                return this.parent.createNewFile();
            }
            this.plugin.saveResource(this.path, false);
            return true;
        } catch (IOException e) {
            throw new IllegalArgumentException("파일 생성에 실패했습니다.", e);
        }
    }

    public boolean removeExistsFile() {
        return exists() && this.parent.delete();
    }

    public abstract boolean save();
    public abstract boolean reload();
    public abstract boolean exists();

    public abstract void remove(String path);
    public abstract void set(String path, Object value);
    public abstract void add(String path, Object value);

    public abstract int getInt(String path);
    public abstract int getInt(String path, int def);

    public abstract long getLong(String path);
    public abstract long getLong(String path, long def);

    public abstract float getFloat(String path);
    public abstract float getFloat(String path, float def);

    public abstract double getDouble(String path);
    public abstract double getDouble(String path, double def);

    public abstract Number getNumber(String path);
    public abstract Number getNumber(String path, Number def);

    public abstract boolean getBoolean(String path);
    public abstract boolean getBoolean(String path, boolean def);

    public abstract String getString(String path);
    public abstract String getString(String path, String def);

    public abstract List<String> getStringList(String path);
    public abstract List<String> getStringList(String path, List<String> def);

    public abstract boolean contains(String path);

    public abstract File getAsouluteFile();
    public abstract JsonObject getJsonConfig();
    public abstract YamlConfiguration getYamlConfig();
}