package nl.han.oose.sapporo.persistence.datasource;

import java.io.IOException;
import java.util.Properties;

public class DatabaseProperties {
    private Properties properties;

    DatabaseProperties() {
        readProperties();
    }

    private Properties readProperties() {
        properties = new Properties();
        try {
            properties.load(getClass().getResourceAsStream("/database.properties"));
        } catch (IOException ex) {
            //TODO change to a mapped exception
            System.out.println("Exception");
        }
        return properties;
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
