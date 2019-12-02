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
    final String DB_URL = "jdbc:h2:mem:TestDatabase";

    abstract void setFactory(ConnectionFactoryImp connectionFactoryImp);

    @BeforeEach
    private void setup() {
        ConnectionFactoryImp connectionFactoryImp = Mockito.mock(ConnectionFactoryImp.class);
        try {
            when(connectionFactoryImp.getConnection()).thenReturn(DriverManager.getConnection(DB_URL));
            setFactory(connectionFactoryImp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //Inserting the scripts
        try {
            Connection connection = DriverManager.getConnection(DB_URL);
            InputStream resource = getClass().getClassLoader().getResourceAsStream("test-Create-Script.sql");
            RunScript.execute(connection, new InputStreamReader(resource));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    private void destroyDB() {
        try {
            Connection connection = DriverManager.getConnection(DB_URL);
            InputStream resource = getClass().getClassLoader().getResourceAsStream("test-DestroyScript.sql");
            RunScript.execute(connection, new InputStreamReader(resource));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
