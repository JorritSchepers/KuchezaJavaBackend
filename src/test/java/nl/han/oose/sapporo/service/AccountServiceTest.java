package nl.han.oose.sapporo.service;

import nl.han.oose.sapporo.dto.LoginDTO;
import nl.han.oose.sapporo.dto.TokenDTO;
import nl.han.oose.sapporo.dto.UserDTO;
import nl.han.oose.sapporo.persistence.IAccountDAO;
import nl.han.oose.sapporo.service.exception.UserAlreadyLoggedOutException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class AccountServiceTest {
    private AccountServiceImp sut = new AccountServiceImp();
    private UserDTO userDTO = new UserDTO();
    private LoginDTO loginDTO = new LoginDTO();
    private String token = "1234";
    private IAccountDAO accountDAO;

    AccountServiceTest() {
        accountDAO = Mockito.mock(IAccountDAO.class);
        sut.setAccountDAO(accountDAO);
        sut.setCustomUuid(() -> {return token;});
    }

    @Test
    void registerUserCallsAddUser() {
        sut.registerUser(userDTO);
        Mockito.verify(accountDAO, Mockito.times(1)).addUser(userDTO);
    }

    @Test
    void registerUserReturnsToken() {
        TokenDTO result = sut.registerUser(userDTO);
        assertEquals(token, result.getToken());
    }

    @Test
    void loginUserCallsCheckUser() {
        Mockito.when(accountDAO.checkUser(loginDTO)).thenReturn(userDTO);
        sut.loginUser(loginDTO);
        Mockito.verify(accountDAO, Mockito.times(1)).checkUser(loginDTO);
    }

    @Test
    void logoutUserThrowsNoException() {
        Mockito.when(accountDAO.checkUser(loginDTO)).thenReturn(userDTO);
        sut.setCustomUuid(() -> {return token;});
        TokenDTO t = sut.loginUser(loginDTO);
        assertDoesNotThrow(() -> {sut.logoutUser(t.getToken());});
    }

    @Test
    void logoutUserThrowsException() {
        assertThrows(UserAlreadyLoggedOutException.class,() -> {sut.logoutUser("12345");});
    }
}