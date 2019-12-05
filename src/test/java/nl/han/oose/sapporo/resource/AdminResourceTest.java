package nl.han.oose.sapporo.resource;

import nl.han.oose.sapporo.dto.UserDTO;
import nl.han.oose.sapporo.service.IAccountService;
import nl.han.oose.sapporo.service.IAdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class AdminResourceTest {
    private final String MOCKED_TOKEN = "1234";
    private final UserDTO MOCKED_USER = new UserDTO();

    private AdminResource sut;
    private IAccountService mockedAccountService;
    private IAdminService mockedAdminService;

    @BeforeEach
    void setUp() {
        sut = new AdminResource();
        mockedAccountService = Mockito.mock(IAccountService.class);
        sut.setAccountService(mockedAccountService);
        Mockito.when(mockedAccountService.verifyToken(MOCKED_TOKEN)).thenReturn(MOCKED_USER);
        mockedAdminService = Mockito.mock(IAdminService.class);
        sut.setAdminService(mockedAdminService);
    }

    @Test
    void getAllNonAdminUsersCallsVerifyTokenInAccountService() {
        sut.getAllNonAdminUsers(MOCKED_TOKEN);
        Mockito.verify(mockedAccountService, Mockito.times(1)).verifyToken(MOCKED_TOKEN);
    }

    @Test
    void getAllNonAdminUsersCallsGetAllNonAdminUsersInAdminService() {
        sut.getAllNonAdminUsers(MOCKED_TOKEN);
        Mockito.verify(mockedAdminService, Mockito.times(1)).getAllNonAdminUsers(MOCKED_USER);
    }
}
