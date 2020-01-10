package nl.han.oose.sapporo.persistence.datasource;

import java.sql.Connection;
import java.sql.SQLException;

interface ConnectionFactory {
    Connection getConnection() throws SQLException;
}