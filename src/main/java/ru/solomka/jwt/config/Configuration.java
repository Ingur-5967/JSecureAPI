package ru.solomka.jwt.config;

import org.jetbrains.annotations.NotNull;
import ru.solomka.jwt.SecureLoader;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.Properties;

public abstract class Configuration {

    private final SecureLoader loader;

    public Configuration(SecureLoader loader) {
        this.loader = loader;
    }

    public Configuration(@NotNull Class<? extends SecureLoader> clazz) {
        try {
            this.loader = clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Object> getValue(String path, String parameter) {
        return Optional.of(getPropertiesFile(path).getProperty(parameter));
    }

    public Optional<Object> getValueOrDefault(String path, String parameter, String defaultValue) {
        return Optional.of(getPropertiesFile(path).getProperty(parameter, defaultValue));
    }

    private @NotNull Properties getPropertiesFile(String path) {
        Properties properties = new Properties();
        try {
            properties.load(loader.getClass().getClassLoader().getResourceAsStream(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties;
    }
}