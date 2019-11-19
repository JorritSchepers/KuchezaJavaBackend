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

    CustomHex customHex;

    AccountDAOImp() {
        customHex = (String information) -> {
            return DigestUtils.sha256Hex(information);
        };
    }

    @Override
    public void addUser(UserDTO userDTO) {
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
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM user WHERE name = ? AND password = ?");
            statement.setString(1, loginDTO.getName());
            statement.setString(2, hexInformation(loginDTO.getPassword()));

            var result = statement.executeQuery();
            if (result.next()) {
                UserDTO user = new UserDTO(
                        result.getString("name"),
                        result.getString("password"),
                        result.getString("email"));
                return user;
            }
        } catch (SQLException e) {

        }
        throw new InvalidLoginInformationException();
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

