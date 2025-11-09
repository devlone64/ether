package io.github.lone64.ether.interfaces;

import io.github.lone64.ether.api.EtherAPI;
import io.github.lone64.ether.api.command.AbstractCommand;
import io.github.lone64.ether.api.config.AbstractConfig;
import io.github.lone64.ether.api.gui.AbstractGui;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.logging.Logger;

@Getter
@SuppressWarnings("all")
public class EtherInterface implements EtherAPI {
    private final Logger LOGGER = Logger.getLogger(getClass().getSimpleName());

    private final Map<String, PluginCommand> COMMANDS = new HashMap<>();

    @Override
    public void openGui(Player player, AbstractGui abstractGui) {
        abstractGui.open(player);
    }

    @Override
    public void registerConfig(AbstractConfig config) {
        if (!config.exists() && !config.createNewFile()) {
            LOGGER.warning("Config file could not be created.");
        }
    }

    @Override
    public void registerCommand(JavaPlugin parent, AbstractCommand abstractCommand) {
        var commandMap = getCommandMap();
        if (commandMap == null) return;

        unregisterCommand(abstractCommand.getName().toLowerCase());

        var pluginCommand = createPluginCommand(parent, abstractCommand.getName().toLowerCase());
        if (pluginCommand == null) return;

        pluginCommand.setUsage(abstractCommand.getUsage());
        pluginCommand.setAliases(Arrays.stream(abstractCommand.getAliases()).toList());
        pluginCommand.setDescription(abstractCommand.getDescription());
        pluginCommand.setPermission(abstractCommand.getPermission());
        pluginCommand.setPermissionMessage(abstractCommand.getPermissionMessage());
        pluginCommand.setExecutor(abstractCommand::onCommand);
        pluginCommand.setTabCompleter(abstractCommand::onTabComplete);

        commandMap.register(parent.getName(), pluginCommand);

        COMMANDS.put(abstractCommand.getName().toLowerCase(), pluginCommand);
        COMMANDS.put(parent.getName() + ":" + abstractCommand.getName().toLowerCase(), pluginCommand);

        Arrays.stream(abstractCommand.getAliases()).toList().forEach(alias -> {
            var aliasCommand = createPluginCommand(parent, alias.toLowerCase());
            if (aliasCommand == null) return;

            aliasCommand.setUsage(abstractCommand.getUsage());
            aliasCommand.setDescription(abstractCommand.getDescription());
            aliasCommand.setPermission(abstractCommand.getPermission());
            aliasCommand.setPermissionMessage(abstractCommand.getPermissionMessage());

            aliasCommand.setExecutor(abstractCommand::onCommand);
            aliasCommand.setTabCompleter(abstractCommand::onTabComplete);

            commandMap.register(parent.getName(), aliasCommand);

            COMMANDS.put(alias.toLowerCase(), aliasCommand);
            COMMANDS.put(parent.getName() + ":" + alias.toLowerCase(), aliasCommand);
        });
    }

    @Override
    public void unregisterCommand(String name) {
        var commandMap = getCommandMap();
        if (commandMap == null) return;

        var command = commandMap.getCommand(name.toLowerCase());
        if (command == null || command.getName().equalsIgnoreCase(name)) return;

        command.unregister(commandMap);
        COMMANDS.remove(name.toLowerCase());
    }

    @Override
    public ItemStack createItemStack(Material type, Consumer<ItemMeta> consumer) {
        return createItemStack(type, 1, consumer);
    }

    @Override
    public ItemStack createItemStack(Material type, int amount, Consumer<ItemMeta> consumer) {
        return createItemStack(new ItemStack(type, amount), consumer);
    }

    @Override
    public ItemStack createItemStack(ItemStack itemStack, Consumer<ItemMeta> consumer) {
        final ItemMeta meta = itemStack.getItemMeta();
        consumer.accept(meta);
        itemStack.setItemMeta(meta);
        return itemStack.clone();
    }

    private PluginCommand createPluginCommand(Plugin plugin, String name) {
        try {
            var commandMap = getCommandMap();
            if (commandMap == null) return null;

            var constructor = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
            constructor.setAccessible(true);

            var command = constructor.newInstance(name, plugin);
            commandMap.register(plugin.getName(), command);
            return command;
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new IllegalArgumentException("PluginCommand cannot create.", e);
        }
    }

    private CommandMap getCommandMap() {
        try {
            var serverClass = Bukkit.getServer().getClass();
            var field = serverClass.getDeclaredField("commandMap");
            field.setAccessible(true);
            return (CommandMap) field.get(Bukkit.getServer());
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            throw new IllegalArgumentException("CommandMap cannot load.", e);
        }
    }
}