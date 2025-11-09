package io.github.lone64.ether.api.gui;

import org.bukkit.event.inventory.InventoryType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Gui {
    String name();
    int rows() default 6;
    InventoryType type() default InventoryType.CHEST;
}