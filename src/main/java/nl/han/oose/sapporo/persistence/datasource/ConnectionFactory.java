package nl.han.oose.sapporo.persistence.datasource;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionFactory {
    Connection makeConnection() throws SQLException;
}