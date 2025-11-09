package io.github.lone64.ether;

import io.github.lone64.ether.interfaces.EtherInterface;
import io.github.lone64.ether.api.EtherAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

public final class EtherPlugin extends JavaPlugin {
    public static EtherPlugin INSTANCE;

    @Override
    public void onLoad() {
        INSTANCE = this;
    }

    @Override
    public void onEnable() {
        Bukkit.getServicesManager().register(EtherAPI.class, new EtherInterface(), this, ServicePriority.Highest);
    }
}