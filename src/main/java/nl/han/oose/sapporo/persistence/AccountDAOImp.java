package nl.han.oose.sapporo.persistence;

import nl.han.oose.sapporo.dto.LoginDTO;
import nl.han.oose.sapporo.dto.UserDTO;
import nl.han.oose.sapporo.persistence.datasource.ConnectionFactoryImp;
import nl.han.oose.sapporo.persistence.exception.AccountAlreadyExistsException;
import nl.han.oose.sapporo.persistence.exception.InvalidLoginInformationException;
import nl.han.oose.sapporo.persistence.exception.PersistenceException;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AccountDAOImp implements IAccountDAO {
    private ConnectionFactoryImp connectionFactory;

    @Inject
    public void setConnectionFactory(ConnectionFactoryImp connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public void addUser(UserDTO userDTO) {
        final int DUPLICATE_VALUE_CODE = 1062;
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO user (name,password,email) VALUES (?,?,?)");
            statement.setString(1, userDTO.getName());
            statement.setString(2, userDTO.getPassword());
            statement.setString(3, userDTO.getEmail());

            statement.execute();
        } catch (SQLException e) {
            if (e.getErrorCode() == DUPLICATE_VALUE_CODE) {
                throw new AccountAlreadyExistsException();
            } else {
                System.out.println(e.getMessage());
                throw new PersistenceException();
            }
        }
    }

    @Override
    public UserDTO getUser(LoginDTO loginDTO) {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM user WHERE email = ? AND password = ?");
            statement.setString(1, loginDTO.getEmail());
            statement.setString(2, loginDTO.getPassword());

            var result = statement.executeQuery();
            if (result.next()) {
                return new UserDTO(
                        result.getInt("userID"),
                        result.getString("name"),
                        result.getString("password"),
                        result.getString("email"),
                        result.getBoolean("admin"));
            }
        } catch (SQLException e) {
            throw new PersistenceException();
        }
        throw new InvalidLoginInformationException();
    }
}

