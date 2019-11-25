package nl.han.oose.sapporo.resource;

import nl.han.oose.sapporo.dto.FarmDTO;
import nl.han.oose.sapporo.dto.UserDTO;
import nl.han.oose.sapporo.service.IAccountService;
import nl.han.oose.sapporo.service.IFarmService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class FarmResourceTest {
    private FarmResource sut = new FarmResource();
    private IAccountService accountService;
    private IFarmService farmService;
    private String token = "123456";
    private UserDTO user = new UserDTO();

    FarmResourceTest() {
        accountService = Mockito.mock(IAccountService.class);
        farmService = Mockito.mock(IFarmService.class);
        sut.setAccountService(accountService);
        sut.setFarmService(farmService);
        Mockito.when(accountService.verifyToken(token)).thenReturn(user);
        Mockito.when(farmService.getFarm(user)).thenReturn(new FarmDTO(1,1));
    }

    @Test
    void getFarmCallsVerifyToken(){
        sut.getFarm(token);
        Mockito.verify(accountService, Mockito.times(1)).verifyToken(token);
    }

    @Test
    void getFarmCallsGetFarm(){
        sut.getFarm(token);
        Mockito.verify(farmService, Mockito.times(1)).getFarm(user);
    }

}