package nl.han.oose.sapporo.service;
import nl.han.oose.sapporo.dto.UserDTO;
import nl.han.oose.sapporo.persistence.IAccountDAO;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class AccountServiceTest {
    private AccountServiceImp sut = new AccountServiceImp();
    private UserDTO userDTO = new UserDTO();
    private IAccountDAO accountDAO;

    AccountServiceTest() {
        accountDAO = Mockito.mock(IAccountDAO.class);
        sut.setAccountDAO(accountDAO);
    }

    @Test
    void registerUserCallsAddUser() {
        sut.registerUser(userDTO);
        Mockito.verify(accountDAO, Mockito.times(1)).addUser(userDTO);
    }

    @Test
    void registerUserReturnsToken() {
        //TODO write test when login is made. Because login will also make the token system
    }
}
