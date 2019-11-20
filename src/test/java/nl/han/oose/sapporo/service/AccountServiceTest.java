package nl.han.oose.sapporo.service;
import nl.han.oose.sapporo.dto.LoginDTO;
import nl.han.oose.sapporo.dto.TokenDTO;
import nl.han.oose.sapporo.dto.UserDTO;
import nl.han.oose.sapporo.persistence.IAccountDAO;
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
        sut.loginUser(loginDTO);
        Mockito.verify(accountDAO, Mockito.times(1)).checkUser(loginDTO);
    }
}
