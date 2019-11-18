package nl.han.oose.sapporo.persistence;

import nl.han.oose.sapporo.persistence.datasource.ConnectionFactoryImp;
import org.h2.tools.RunScript;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.mockito.Mockito.when;

public abstract class DAOTest {
    String dbUrl = "jdbc:h2:mem:TestDatabase";

    abstract void setfactory (ConnectionFactoryImp connectionFactoryImp);

    @BeforeEach
    private void setup() {
        ConnectionFactoryImp connectionFactoryImp = Mockito.mock(ConnectionFactoryImp.class);
        try {
            when(connectionFactoryImp.makeConnection()).thenReturn(DriverManager.getConnection(dbUrl));
            setfactory (connectionFactoryImp);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Inserting the scripts
        try {
            Connection connection = DriverManager.getConnection(dbUrl);
            InputStream resource = getClass().getClassLoader().getResourceAsStream("test-Create-Script.sql");
            RunScript.execute(connection, new InputStreamReader(resource));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    private void destroyDB() {
        try {
            Connection connection = DriverManager.getConnection(dbUrl);
            InputStream resource = getClass().getClassLoader().getResourceAsStream("test-DestroyScript.sql");
            RunScript.execute(connection, new InputStreamReader(resource));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
