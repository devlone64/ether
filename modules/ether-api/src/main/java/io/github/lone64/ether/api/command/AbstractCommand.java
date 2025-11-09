package io.github.lone64.ether.api.command;

import io.github.lone64.ether.api.utils.Utils;
import lombok.Getter;
import org.bukkit.command.CommandSender;

import java.util.List;

@Getter
public abstract class AbstractCommand implements Utils {
    public final String name;
    public final String description;
    public final String[] aliases;
    public final String usage;
    public final String permission;
    public final String permissionMessage;

    public AbstractCommand() {
        final Command command = this.getClass().getAnnotation(Command.class);
        if (this.getClass().isAnnotationPresent(Command.class)) {
            this.name = command.name();
            this.description = command.description();
            this.aliases = command.aliases();
            this.usage = command.usage();
            this.permission = command.permission();
            this.permissionMessage = command.permissionMessage();
        } else {
            throw new IllegalArgumentException("@Command annotation is required");
        }
    }

    public boolean onCommand(final CommandSender sender, final org.bukkit.command.Command command, final String arg, final String[] args) {
        execute(sender, args);
        return true;
    }

    public List<String> onTabComplete(final CommandSender sender, final org.bukkit.command.Command command, final String arg, final String[] args) {
        return complete(sender, args);
    }

    protected abstract void execute(CommandSender sender, String[] args);
    protected abstract List<String> complete(CommandSender sender, String[] args);
}