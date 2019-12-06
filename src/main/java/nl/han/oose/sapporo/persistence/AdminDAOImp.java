package nl.han.oose.sapporo.persistence;

import nl.han.oose.sapporo.dto.AllUsersDTO;
import nl.han.oose.sapporo.dto.UserDTO;
import nl.han.oose.sapporo.persistence.datasource.ConnectionFactoryImp;
import nl.han.oose.sapporo.persistence.exception.PersistenceException;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDAOImp implements IAdminDAO {
    private ConnectionFactoryImp connectionFactory;

    @Inject
    public void setConnectionFactory(ConnectionFactoryImp connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public boolean isAdmin(UserDTO userDTO) {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM user WHERE email = ? AND admin = true");
            statement.setString(1, userDTO.getEmail());
            ResultSet result = statement.executeQuery();
            return result.next();
        } catch (SQLException e) {
            throw new PersistenceException();
        }
    }

    @Override
    public AllUsersDTO getAllNonAdminUsers() {
        try (Connection connection = connectionFactory.getConnection()) {
            AllUsersDTO users = new AllUsersDTO();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE admin = false");
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                users.addUser(new UserDTO(
                        result.getInt("userID"),
                        result.getString("name"),
                        result.getString("password"),
                        result.getString("email"),
                        result.getBoolean("admin")
                ));
            }
            return users;
        } catch (SQLException e) {
            throw new PersistenceException();
        }
    }

    @Override
    public void deleteUser(int userID) {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM user WHERE userID = ?");
            preparedStatement.setInt(1, userID);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new PersistenceException();
        }
    }
}
