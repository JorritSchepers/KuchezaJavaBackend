package nl.han.oose.sapporo.persistence;

import nl.han.oose.sapporo.dto.InventoryDTO;
import nl.han.oose.sapporo.dto.UserDTO;
import nl.han.oose.sapporo.persistence.datasource.ConnectionFactoryImp;
import nl.han.oose.sapporo.persistence.exception.InsufficientFundsException;
import nl.han.oose.sapporo.persistence.exception.InsufficientWaterException;
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
                    ("SELECT money FROM inventory where userID = ?");
            statement.setInt(1,userDTO.getID());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                moneyInInventory = resultSet.getFloat("money");
            } else {
                return false;
            }

            if (moneyInInventory < amount){
                throw new InsufficientFundsException();
            }

            return (true);
        } catch (SQLException e) {
            throw new PersistenceException();
        }
    }

    @Override
    public void lowerSaldo(float amount, UserDTO userDTO) {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement
                    ("update inventory set money = money-? where userID = ?");
            statement.setFloat(1,amount);
            statement.setInt(2,userDTO.getID());
            statement.execute();
        } catch (SQLException e) {
            throw new PersistenceException();
        }
    }

    @Override
    public void increaseSaldo(float amount, UserDTO userDTO) {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement
                    ("update inventory set money = money+? where userID = ?");
            statement.setFloat(1,amount);
            statement.setInt(2,userDTO.getID());
            statement.execute();
        } catch (SQLException e) {
            throw new PersistenceException();
        }
    }

    @Override
    public boolean checkWater(int amount, UserDTO userDTO) {
        try (Connection connection = connectionFactory.getConnection()) {
            int waterInInventory = 0;
            PreparedStatement statement = connection.prepareStatement
                    ("SELECT water FROM inventory where userID = ?");
            statement.setInt(1,userDTO.getID());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                waterInInventory = resultSet.getInt("water");
            }

            if (waterInInventory < amount){
                throw new InsufficientWaterException();
            }

            return (true);
        } catch (SQLException e) {
            throw new PersistenceException();
        }
    }

    @Override
    public void lowerWater(int amount, UserDTO userDTO) {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement
                    ("update inventory set water = water-? where userID = ?");
            statement.setFloat(1,amount);
            statement.setInt(2,userDTO.getID());
            statement.execute();
        } catch (SQLException e) {
            throw new PersistenceException();
        }
    }

    @Override
    public void increaseWater(int amount, UserDTO userDTO) {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement
                    ("update inventory set water = water+? where userID = ?");
            statement.setFloat(1,amount);
            statement.setInt(2,userDTO.getID());
            statement.execute();
        } catch (SQLException e) {
            throw new PersistenceException();
        }
    }

    @Override
    public void createInventory(UserDTO user) {
        final int STARTMONEY = 10000;
        final int STARTWATER = 20000;
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement
                    ("INSERT INTO inventory values (?,?,?)");
            statement.setInt(1,user.getID());
            statement.setInt(2,STARTWATER);
            statement.setInt(3,STARTMONEY);
            statement.execute();
        } catch (SQLException e) {
            throw new PersistenceException();
        }
    }

    @Override
    public InventoryDTO getInventory(UserDTO user) {
        try (Connection connection = connectionFactory.getConnection()) {
            InventoryDTO inventory = null;
            PreparedStatement statement = connection.prepareStatement
                    ("select * from inventory where userID = ?");
            statement.setInt(1,user.getID());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("userID");
                int money = resultSet.getInt("money");
                int water = resultSet.getInt("water");
                inventory = new InventoryDTO(id,money,water);
            }
            return  inventory;
        } catch (SQLException e) {
            throw new PersistenceException();
        }
    }
}