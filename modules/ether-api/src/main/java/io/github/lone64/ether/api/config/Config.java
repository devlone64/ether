package io.github.lone64.ether.api.config;

import io.github.lone64.ether.api.config.impl.JsonConfig;
import io.github.lone64.ether.api.config.impl.YamlConfig;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Config {
    String name();
    String path() default "";
    Type type() default Type.YAML;

    enum Type {
        YAML("yml", YamlConfig.class),
        JSON("json", JsonConfig.class);

        public final String extension;
        public final Class<? extends AbstractConfig> config;

        Type(String extension, Class<? extends AbstractConfig> config) {
            this.extension = extension;
            this.config = config;
        }
    }
}