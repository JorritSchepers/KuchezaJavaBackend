package nl.han.oose.sapporo.persistence;

import nl.han.oose.sapporo.dto.LoginDTO;
import nl.han.oose.sapporo.dto.UserDTO;
import nl.han.oose.sapporo.persistence.datasource.ConnectionFactoryImp;
import nl.han.oose.sapporo.persistence.exception.AccountAlreadyExistsException;
import nl.han.oose.sapporo.persistence.exception.InvalidLoginInformationException;
import nl.han.oose.sapporo.persistence.exception.PersistenceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.*;


public class AccountDAOTest extends DAOTest {
    private AccountDAOImp sut = new AccountDAOImp();
    private UserDTO userDTO = new UserDTO("TestUser", "wachtwoord", "TestUser@Hotmail.com");
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

    @Test
    void correctLoginInformationReturnsUserDTO() {
        sut.setCustomHex((String information) -> {return "wachtwoord";});
        UserDTO result = sut.checkUser(loginDTO);
        assertEquals(loginDTO.getPassword(),result.getPassword());
    }

    @Test
    void inCorrectLoginInformationThrowsException() {
        sut.setCustomHex((String information) -> {return "wachtwoord";});
        assertThrows(InvalidLoginInformationException.class, ()-> {
            sut.checkUser(new LoginDTO("fout","fout"));
        });
    }
}