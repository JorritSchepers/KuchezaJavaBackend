package nl.han.oose.sapporo.service;

import nl.han.oose.sapporo.dto.AllUsersDTO;
import nl.han.oose.sapporo.dto.UserDTO;
import nl.han.oose.sapporo.persistence.IAdminDAO;
import nl.han.oose.sapporo.service.exception.UserIsNotAnAdministratorException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class AdminServiceImpTest {
    private final UserDTO ADMIN_USER = new UserDTO(1, "admin", "admin","admin@admin.nl", true);
    private final UserDTO NON_ADMIN_USER = new UserDTO(2, "player", "password","player@player.nl", false);
    private final AllUsersDTO MOCKED_ALL_USERS = new AllUsersDTO();

    private AdminServiceImp sut;
    private IAdminDAO mockedAdminDAO;

    @BeforeEach
    void setUp() {
        this.sut = new AdminServiceImp();
        mockedAdminDAO = Mockito.mock(IAdminDAO.class);
        this.sut.setAdminDAO(mockedAdminDAO);
        Mockito.when(mockedAdminDAO.isAdmin(ADMIN_USER)).thenReturn(true);
    }

    @Test
    void checkIfUserIsAdminDoesNothingWhenUserIsAnAdmin() {
        sut.checkIfUserIsAdmin(ADMIN_USER);
    }

    @Test
    void checkIfUserIsAdminThrowsUserIsNotAnAdministratorExceptionWhenUserIsNotAnAdmin() {
        Mockito.when(mockedAdminDAO.isAdmin(NON_ADMIN_USER)).thenReturn(false);
        assertThrows(UserIsNotAnAdministratorException.class, () -> sut.checkIfUserIsAdmin(NON_ADMIN_USER));
    }

    @Test
    void checkIfUserIsAdminCallsGetAllNonAdminUsersInAdminDAO() {
        sut.checkIfUserIsAdmin(ADMIN_USER);
        Mockito.verify(mockedAdminDAO, Mockito.times(1)).isAdmin(ADMIN_USER);
    }

    @Test
    void getAllNonAdminUsersCallsGetAllNonAdminUsersInAdminDAO() {
        sut.getAllNonAdminUsers(ADMIN_USER);
        Mockito.verify(mockedAdminDAO, Mockito.times(1)).getAllNonAdminUsers();
    }

    @Test
    void getAllNonAdminUsersReturnAllUsersDTO() {
        Mockito.when(mockedAdminDAO.getAllNonAdminUsers()).thenReturn(MOCKED_ALL_USERS);
        assertEquals(sut.getAllNonAdminUsers(ADMIN_USER), MOCKED_ALL_USERS);
    }
}
