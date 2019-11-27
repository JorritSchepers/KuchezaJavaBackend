package nl.han.oose.sapporo.service;

import nl.han.oose.sapporo.dto.LoginDTO;
import nl.han.oose.sapporo.dto.TokenDTO;
import nl.han.oose.sapporo.dto.UserDTO;
import nl.han.oose.sapporo.persistence.IAccountDAO;
import nl.han.oose.sapporo.service.exception.UserAlreadyLoggedOutException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class AccountServiceTest {
    private AccountServiceImp sut = new AccountServiceImp();
    private UserDTO userDTO = new UserDTO(1, "naam", "email", "wachtwoord");
    private LoginDTO loginDTO = new LoginDTO("email", "wachtwoord");
    private String token = "1234";
    private IAccountDAO accountDAO;

    @BeforeEach
    void settingUp() {
        accountDAO = Mockito.mock(IAccountDAO.class);
        Mockito.when(accountDAO.getUser(loginDTO)).thenReturn(userDTO);
        sut.setAccountDAO(accountDAO);
        sut.setCustomUuid(() -> token);
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
    void logoutUserThrowsExceptionWhenUserIsAlreadyLoggedOut() {
        assertThrows(UserAlreadyLoggedOutException.class, () -> {
            sut.logoutUser("12345");
        });
    }

    @Test
    void loginUserReturnsRandomToken(){
        sut.loginUser(loginDTO);
        Assertions.assertEquals(token,sut.loginUser(loginDTO).getToken());
    }

    @Test
    void verifyTokenReturnsThrowsExceptionWhenEmpty(){
        assertThrows(UserAlreadyLoggedOutException.class, () -> {
            sut.verifyToken(token);
        });
    }
}