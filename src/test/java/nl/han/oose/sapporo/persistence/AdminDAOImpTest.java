package nl.han.oose.sapporo.persistence;

import nl.han.oose.sapporo.dto.UserDTO;
import nl.han.oose.sapporo.persistence.datasource.ConnectionFactoryImp;
import org.junit.jupiter.api.Test;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

class AdminDAOImpTest extends DAOTest {
    private final UserDTO ADMIN_USER = new UserDTO("admin", "admin", "admin@admin.com", true);
    private final UserDTO NON_ADMIN_USER = new UserDTO("TestUser", "wachtwoord", "oose.sapporo@gmail.com", false);
    private final int USER_ID = 1;

    private AdminDAOImp sut = new AdminDAOImp();

    @Override
    void setFactory(ConnectionFactoryImp connectionFactoryImp) {
        sut.setConnectionFactory(connectionFactoryImp);
    }

    @Test
    void isAdminReturnsTrueWhenUserIsAnAdmin() {
        assertTrue(sut.isAdmin(ADMIN_USER));
    }

    @Test
    void isAdminReturnsFalseWhenUserIsNotAnAdmin() {
        assertFalse(sut.isAdmin(NON_ADMIN_USER));
    }

    @Test
    void getAllNonAdminUsersReturnsCorrectTotalUsers() {
        assertEquals(3, sut.getAllNonAdminUsers().getUsers().size());
    }

    @Test
    void deleteUserRemovesOneUserFromDB() {
        int oldAmount = getAmountOfUsers();
        sut.deleteUser(USER_ID);
        int newAmount = getAmountOfUsers();
        assertEquals(oldAmount-1, newAmount);
    }

    @Test
    void deleteUserRemovesCorrectUserInDB() {
        sut.deleteUser(USER_ID);
        assertFalse(userExists(USER_ID));
    }

    private int getAmountOfUsers() {
        int amountOfUsers = 0;
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            PreparedStatement statement = connection.prepareStatement("select count(userID) as total from user");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                amountOfUsers = resultSet.getInt("total");
            }
        } catch (SQLException ignored) {
        }
        return amountOfUsers;
    }

    private Boolean userExists(int userID) {
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            PreparedStatement statement = connection.prepareStatement("select * from user where userID = ?");
            statement.setInt(1, userID);
            ResultSet resultSet = statement.executeQuery();
            return (resultSet.next());
        } catch (SQLException ignored) {
        }
        return false;
    }

}
