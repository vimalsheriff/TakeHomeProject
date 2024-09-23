package io.testapi.rmsmedia.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

public class ConfigReader {

    private static ConfigReader INSTANCE;
    private static Properties properties;

    private ConfigReader() {
        loadProperties();
    }

    public static ConfigReader getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ConfigReader();
        }

        return INSTANCE;
    }

    private void loadProperties() {
        // Load properties from config file

        properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new IOException("Sorry, unable to find config.properties file under resources");
            }
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String getPropertyValue(String property, String exceptionMessage) {
        return Optional.of(properties.getProperty(property))
                .orElseThrow(() -> new IllegalArgumentException(exceptionMessage));

    }
}
