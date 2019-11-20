package nl.han.oose.sapporo.resource;

import nl.han.oose.sapporo.dto.LoginDTO;
import nl.han.oose.sapporo.dto.TokenDTO;
import nl.han.oose.sapporo.dto.UserDTO;
import nl.han.oose.sapporo.service.IAccountService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.*;

class AccountResourceTest {
    private UserDTO userDTO = new UserDTO();
    private TokenDTO token = new TokenDTO(userDTO, "12345677");
    private LoginDTO loginDTO = new LoginDTO();
    private AccountResource sut = new AccountResource();
    private IAccountService accountService;

    AccountResourceTest() {
        accountService = Mockito.mock(IAccountService.class);
        sut.setAccountService(accountService);

        Mockito.when(accountService.registerUser(userDTO)).thenReturn(token);
        Mockito.when(accountService.loginUser(loginDTO)).thenReturn(token);
    }

    @Test
    void buildTest() {
        assertEquals(true, true);
    }

    @Test
    void registerUserCallsregisterUserFunction() {
        sut.registerUser(userDTO);
        Mockito.verify(accountService, Mockito.times(1)).registerUser(userDTO);
    }

    @Test
    void registerUserReturnsRightToken() {
        Response response = sut.registerUser(userDTO);
        Assertions.assertEquals(token,response.getEntity());
        Assertions.assertEquals(201,response.getStatus());
    }

    @Test
    void loginCallsLoginUserFunction() {
        sut.login(loginDTO);
        Mockito.verify(accountService, Mockito.times(1)).loginUser(loginDTO);
    }

    @Test
    void loginUserReturnsRightToken() {
        Response response = sut.login(loginDTO);
        Assertions.assertEquals(token,response.getEntity());
        Assertions.assertEquals(Response.Status.OK.getStatusCode(),response.getStatus());
    }
}