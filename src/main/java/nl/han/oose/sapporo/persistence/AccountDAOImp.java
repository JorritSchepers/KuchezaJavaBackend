package nl.han.oose.sapporo.persistence;

import nl.han.oose.sapporo.dto.UserDTO;
import nl.han.oose.sapporo.persistence.datasource.ConnectionFactoryImp;
import nl.han.oose.sapporo.persistence.exception.AccountAlreadyExistsException;
import nl.han.oose.sapporo.persistence.exception.PersistenceException;
import org.apache.commons.codec.digest.DigestUtils;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AccountDAOImp implements IAccountDAO {
    private final int DUPLICATEVALUECODE = 1062;

    private ConnectionFactoryImp connectionFactory;

    @Inject
    public void setConnectionFactory(ConnectionFactoryImp connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public void addUser(UserDTO userDTO) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new PersistenceException();
        }

        try (Connection connection = connectionFactory.makeConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO user (name,password,email) VALUES (?,?,?)");
            statement.setString(1, userDTO.getName());
            statement.setString(2, hexInformation(userDTO.getPassword()));
            statement.setString(3, userDTO.getEmail());

            statement.execute();
        } catch (SQLException e) {
            if (e.getErrorCode() == DUPLICATEVALUECODE){
                throw new AccountAlreadyExistsException();
            }
            throw new PersistenceException();
        }
    }

    @Override
    public String hexInformation(String information) {
        return DigestUtils.sha256Hex(information);
    }
}

