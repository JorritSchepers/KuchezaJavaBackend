package nl.han.oose.sapporo.persistence.datasource;

import nl.han.oose.sapporo.persistence.exception.PersistenceException;

import javax.enterprise.inject.Default;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Default
public class ConnectionFactoryImp implements ConnectionFactory {
    private DatabaseProperties properties = new DatabaseProperties();

    @Override
    public Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new PersistenceException();
        }

        return DriverManager.getConnection(properties.getUrl(), properties.getUser(), properties.getPassword());
    }
}