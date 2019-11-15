package nl.han.oose.sapporo.persistence.datasource;

import javax.enterprise.inject.Default;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Default
public class ConnectionFactoryImp implements ConnectionFactory {
    private DatabaseProperties properties = new DatabaseProperties();

    @Override
    public Connection makeConnection() throws SQLException {
        return DriverManager.getConnection(properties.getUrl(), properties.getUser(), properties.getPassword());
    }
}
