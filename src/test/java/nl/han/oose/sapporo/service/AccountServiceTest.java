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
import org.mockito.asm.tree.IincInsnNode;

import static org.junit.jupiter.api.Assertions.*;

class AccountServiceTest {
    private AccountServiceImp sut = new AccountServiceImp();
    private UserDTO userDTO = new UserDTO(1, "naam", "email", "wachtwoord");
    private LoginDTO loginDTO = new LoginDTO("email", "wachtwoord");
    private String token = "1234";
    private IAccountDAO accountDAO;
    private IInventoryService inventoryService;
    private IFarmService iFarmService;
    private UserDTO newuser =  new UserDTO(1,"name","wachtwoord","email",false);

    @BeforeEach
    void settingUp() {
        accountDAO = Mockito.mock(IAccountDAO.class);
        inventoryService = Mockito.mock(IInventoryService.class);
        iFarmService = Mockito.mock(IFarmService.class);
        Mockito.when(accountDAO.getUser(loginDTO)).thenReturn(userDTO);
        Mockito.when(accountDAO.getUser(new LoginDTO(newuser.getEmail(),newuser.getPassword()))).thenReturn(userDTO);
        sut.setAccountDAO(accountDAO);
        sut.setCustomUuid(() -> token);
        sut.setFarmService(iFarmService);
        sut.setInventoryService(inventoryService);
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
    void registerUserCallsAddUser(){
        sut.registerUser(newuser);
        Mockito.verify(accountDAO, Mockito.times(1)).addUser(newuser);
    }

    @Test
    void registerUserCallsGenerateRandomToken(){
        UserDTO newuser =  new UserDTO(1,"name","wachtwoord","email",false);
        sut.registerUser(newuser);
        Mockito.verify(accountDAO, Mockito.times(1)).addUser(newuser);
    }
}