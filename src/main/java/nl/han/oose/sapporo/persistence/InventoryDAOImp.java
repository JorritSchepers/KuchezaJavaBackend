package nl.han.oose.sapporo.persistence;

import nl.han.oose.sapporo.dto.UserDTO;
import nl.han.oose.sapporo.persistence.datasource.ConnectionFactoryImp;
import nl.han.oose.sapporo.persistence.exception.PersistenceException;

import javax.enterprise.inject.Default;
import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Default
public class InventoryDAOImp implements IInventoryDAO {
    private ConnectionFactoryImp connectionFactory;

    @Inject
    public void setConnectionFactory(ConnectionFactoryImp connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public boolean checkSaldo(float amount, UserDTO userDTO) {
        try (Connection connection = connectionFactory.getConnection()) {
            float moneyInInventory = 0;
            PreparedStatement statement = connection.prepareStatement
                    ("SELECT money FROM inventory INNER JOIN user ON user.userID = inventory.userID where name = ?");
            statement.setString(1,userDTO.getName());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                moneyInInventory = resultSet.getFloat("money");;
            }
            return (moneyInInventory >= amount);
        } catch (SQLException e) {
            throw new PersistenceException();
        }
    }

    @Override
    public void lowerSaldo(float amount, UserDTO userDTO) {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement
                    ("update inventory INNER JOIN user ON user.userID = inventory.userID set money = money-20 where name = ?");
            statement.setString(1,userDTO.getName());
            statement.execute();

            //TODO add error when saldo too low.
        } catch (SQLException e) {
            throw new PersistenceException();
        }
    }
}
