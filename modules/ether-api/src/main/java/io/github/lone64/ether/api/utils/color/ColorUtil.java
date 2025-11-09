package io.github.lone64.ether.api.utils.color;

import lombok.experimental.UtilityClass;
import net.md_5.bungee.api.ChatColor;

@UtilityClass
public class ColorUtil {
    public String format(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}