package nl.han.oose.sapporo.persistence;

import nl.han.oose.sapporo.dto.LoginDTO;
import nl.han.oose.sapporo.dto.UserDTO;
import nl.han.oose.sapporo.persistence.datasource.ConnectionFactoryImp;
import nl.han.oose.sapporo.persistence.exception.InvalidLoginInformationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.*;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AccountDAOTest extends DAOTest {
    private AccountDAOImp sut = new AccountDAOImp();
    private UserDTO userDTO = new UserDTO(1, "TestUser", "wachtwoord", "TestUser@Hotmail.com");
    private LoginDTO loginDTO = new LoginDTO("oose.sapporo@gmail.com", "wachtwoord");

    @Override
    void setfactory(ConnectionFactoryImp connectionFactoryImp) {
        sut.setConnectionFactory(connectionFactoryImp);
    }


    private int getAmountOfUsers() {
        int amountofUsers = 0;
        try (Connection connection = DriverManager.getConnection(dbUrl)) {
            PreparedStatement statement = connection.prepareStatement("select count(userID) as total from user");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                amountofUsers = resultSet.getInt("total");
            }
        } catch (SQLException ignored) {
        }
        return amountofUsers;
    }

    private Boolean userExists(String email) {
        try (Connection connection = DriverManager.getConnection(dbUrl)) {
            PreparedStatement statement = connection.prepareStatement("select * from USER where email = ?");
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            return (resultSet.next());
        } catch (SQLException ignored) {
        }
        return false;
    }

    @Test
    void checkUserWithCorrectLoginInformationReturnsUserDTO() {
        UserDTO expected = new UserDTO(3,"TestUser", "wachtwoord", "oose.sapporo@gmail.com");
        UserDTO result = sut.getUser(loginDTO);
        assertEquals(expected,result);
    }

    @Test
    void checkUserWithInCorrectLoginInformationThrowsException() {
        assertThrows(InvalidLoginInformationException.class, ()-> {
            sut.getUser(new LoginDTO("fout","fout"));
        });
    }

    @Test
    void addUserIncreasesUserAmount() {
        int oldAmount = getAmountOfUsers();
        sut.addUser(userDTO);
        Assertions.assertEquals((oldAmount + 1), getAmountOfUsers());
    }

    @Test
    void userIsInDatabaseAfteraddUser() {
        Assertions.assertFalse(userExists(userDTO.getEmail()));
        sut.addUser(userDTO);
        Assertions.assertTrue(userExists(userDTO.getEmail()));
    }
}