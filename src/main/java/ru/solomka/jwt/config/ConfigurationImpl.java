package ru.solomka.jwt.config;

import org.jetbrains.annotations.NotNull;
import ru.solomka.jwt.SecureLoader;

public class ConfigurationImpl extends Configuration {
    public ConfigurationImpl(@NotNull Class<? extends SecureLoader> clazz) {
        super(clazz);
    }

    public ConfigurationImpl(SecureLoader loader) {
        super(loader);
    }
}