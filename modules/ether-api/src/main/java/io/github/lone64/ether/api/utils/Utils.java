package io.github.lone64.ether.api.utils;

import io.github.lone64.ether.api.utils.color.ColorUtil;

public interface Utils {
    default String format(String message) {
        return ColorUtil.format(message);
    }
}