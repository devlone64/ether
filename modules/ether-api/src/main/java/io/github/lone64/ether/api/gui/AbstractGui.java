package io.github.lone64.ether.api.gui;

import io.github.lone64.ether.api.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractGui implements InventoryHolder, Listener, Utils {
    public final Inventory inventory;

    public AbstractGui(JavaPlugin plugin) {
        final Gui gui = this.getClass().getAnnotation(Gui.class);
        if (this.getClass().isAnnotationPresent(Gui.class)) {
            if (gui.type() == InventoryType.CHEST) {
                this.inventory = Bukkit.createInventory(this, gui.rows() * 9, format(gui.name()));
            } else {
                this.inventory = Bukkit.createInventory(this, gui.type(), format(gui.name()));
            }
            Bukkit.getPluginManager().registerEvents(this, plugin);
        } else {
            throw new IllegalArgumentException("@Gui annotation is required");
        }
    }

    public void open(Player player) {
        init(player);
        menu(player);
        player.openInventory(this.inventory);
    }

    @Override
    public @NotNull Inventory getInventory() {
        return this.inventory;
    }

    public abstract void init(Player player);
    public abstract void menu(Player player);
}