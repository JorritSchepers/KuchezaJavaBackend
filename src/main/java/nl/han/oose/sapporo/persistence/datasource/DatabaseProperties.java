package nl.han.oose.sapporo.persistence.datasource;

import nl.han.oose.sapporo.persistence.exception.UnableToReadDatabasePropertiesException;

import java.io.IOException;
import java.util.Properties;

public class DatabaseProperties {
    private Properties properties;

    DatabaseProperties() {
        readProperties();
    }

    private void readProperties() {
        properties = new Properties();
        try {
            properties.load(getClass().getResourceAsStream("/database.properties"));
        } catch (IOException ex) {
            throw new UnableToReadDatabasePropertiesException();
        }
    }

    public String getUser() {
        return properties.getProperty("db.user");
    }

    String getPassword() {
        return properties.getProperty("db.password");
    }

    String getUrl() {
        return properties.getProperty("db.url");
    }
}
