package nl.han.oose.sapporo.persistence;

import nl.han.oose.sapporo.dto.LoginDTO;
import nl.han.oose.sapporo.dto.UserDTO;
import nl.han.oose.sapporo.persistence.datasource.ConnectionFactoryImp;
import nl.han.oose.sapporo.persistence.exception.AccountAlreadyExistsException;
import nl.han.oose.sapporo.persistence.exception.InvalidLoginInformationException;
import nl.han.oose.sapporo.persistence.exception.PersistenceException;
import nl.han.oose.sapporo.persistence.functionalInterface.CustomHex;
import org.apache.commons.codec.digest.DigestUtils;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AccountDAOImp implements IAccountDAO {
    private ConnectionFactoryImp connectionFactory;
    private int test;

    private CustomHex customHex;

    AccountDAOImp() {
        customHex = (String information) -> {
            return DigestUtils.sha256Hex(information);
        };
    }

    @Override
    public void addUser(UserDTO userDTO) {
        // TODO Move to connection factory
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new PersistenceException();
        }

        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO user (name,password,email) VALUES (?,?,?)");
            statement.setString(1, userDTO.getName());
            statement.setString(2, hexInformation(userDTO.getPassword()));
            statement.setString(3, userDTO.getEmail());

            statement.execute();
        } catch (SQLException e) {
            final int DUPLICATE_VALUE_CODE = 1062;
            if (e.getErrorCode() == DUPLICATE_VALUE_CODE) {
                throw new AccountAlreadyExistsException();
            } else {
                throw new PersistenceException();
            }
        }
    }

    @Override
    public UserDTO checkUser(LoginDTO loginDTO) {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM user WHERE email = ? AND password = ?");
            statement.setString(1, loginDTO.getEmail());
            statement.setString(2, hexInformation(loginDTO.getPassword()));

            var result = statement.executeQuery();
            if (result.next()) {
                UserDTO user = new UserDTO(
                        result.getInt("userID"),
                        result.getString("name"),
                        result.getString("password"),
                        result.getString("email"));
                return user;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        throw new InvalidLoginInformationException();
    }

    public void setCustomHex(CustomHex customHex) {
        this.customHex = customHex;
    }

    @Override
    public String hexInformation(String information) {
        return customHex.shaHex(information);
    }

    @Inject
    public void setConnectionFactory(ConnectionFactoryImp connectionFactory) {
        this.connectionFactory = connectionFactory;
    }
}

