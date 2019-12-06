package nl.han.oose.sapporo.resource;

import nl.han.oose.sapporo.dto.UserDTO;
import nl.han.oose.sapporo.service.IAccountService;
import nl.han.oose.sapporo.service.IAdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class AdminResourceTest {
    private final String TOKEN = "1234";
    private final UserDTO USER = new UserDTO();

    private AdminResource sut;
    private IAccountService mockedAccountService;
    private IAdminService mockedAdminService;

    @BeforeEach
    void setUp() {
        sut = new AdminResource();
        mockedAccountService = Mockito.mock(IAccountService.class);
        sut.setAccountService(mockedAccountService);
        Mockito.when(mockedAccountService.verifyToken(TOKEN)).thenReturn(USER);
        mockedAdminService = Mockito.mock(IAdminService.class);
        sut.setAdminService(mockedAdminService);
    }

    @Test
    void getAllNonAdminUsersCallsVerifyTokenInAccountService() {
        sut.getAllNonAdminUsers(TOKEN);
        Mockito.verify(mockedAccountService, Mockito.times(1)).verifyToken(TOKEN);
    }

    @Test
    void getAllNonAdminUsersCallsGetAllNonAdminUsersInAdminService() {
        sut.getAllNonAdminUsers(TOKEN);
        Mockito.verify(mockedAdminService, Mockito.times(1)).getAllNonAdminUsers(USER);
    }

    @Test
    void deleteUserCallsVerifyTokenInAccountService() {
        sut.getAllNonAdminUsers(TOKEN);
        Mockito.verify(mockedAccountService, Mockito.times(1)).verifyToken(TOKEN);
    }

    @Test
    void deleteUserCallsDeleteUserInAdminService() {
        final int MOCKED_USER_ID = 1;
        sut.deleteUser(TOKEN, MOCKED_USER_ID);
        Mockito.verify(mockedAdminService, Mockito.times(1)).deleteUser(USER, MOCKED_USER_ID);
    }

    @Test
    void deleteUserCallsGetAllNonAdminUsersInAdminService() {
        sut.getAllNonAdminUsers(TOKEN);
        Mockito.verify(mockedAdminService, Mockito.times(1)).getAllNonAdminUsers(USER);
    }
}
