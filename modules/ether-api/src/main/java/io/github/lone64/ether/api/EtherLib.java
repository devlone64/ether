package io.github.lone64.ether.api;

import io.github.lone64.ether.api.command.AbstractCommand;
import io.github.lone64.ether.api.config.AbstractConfig;
import io.github.lone64.ether.api.gui.AbstractGui;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public interface EtherLib {
    void openGui(Player player, AbstractGui abstractGui);

    void registerConfig(AbstractConfig config);

    void registerCommand(JavaPlugin parent, AbstractCommand abstractCommand);
    void unregisterCommand(String name);

    ItemStack createItemStack(Material type, Consumer<ItemMeta> consumer);
    ItemStack createItemStack(Material type, int amount, Consumer<ItemMeta> consumer);
    ItemStack createItemStack(ItemStack itemStack, Consumer<ItemMeta> consumer);

    static @NotNull EtherLib getInstance() {
        final RegisteredServiceProvider<EtherLib> serviceProvider = Bukkit.getServicesManager().getRegistration(EtherLib.class);
        if (serviceProvider != null) return serviceProvider.getProvider();
        throw new IllegalStateException("No EtherLib found!");
    }
}