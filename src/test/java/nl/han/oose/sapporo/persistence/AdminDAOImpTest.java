package nl.han.oose.sapporo.persistence;

import nl.han.oose.sapporo.dto.UserDTO;
import nl.han.oose.sapporo.persistence.datasource.ConnectionFactoryImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdminDAOImpTest extends DAOTest {
    private final UserDTO ADMIN_USER = new UserDTO("admin", "admin", "admin@admin.com", true);
    private final UserDTO NON_ADMIN_USER = new UserDTO("TestUser", "wachtwoord", "oose.sapporo@gmail.com", false);

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
}
