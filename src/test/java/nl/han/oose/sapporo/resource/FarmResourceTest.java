package nl.han.oose.sapporo.resource;

import nl.han.oose.sapporo.dto.FarmDTO;
import nl.han.oose.sapporo.dto.TokenDTO;
import nl.han.oose.sapporo.dto.UserDTO;
import nl.han.oose.sapporo.service.AccountServiceImp;
import nl.han.oose.sapporo.service.FarmServiceImp;
import nl.han.oose.sapporo.service.IAccountService;
import nl.han.oose.sapporo.service.IFarmService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class FarmResourceTest {
    private FarmResource sut;
    private IFarmService farmService;
    private IAccountService accountService;
    private FarmDTO farmDTO;
    private UserDTO userDTO;
    private TokenDTO tokenDTO;

    @BeforeEach
    void setup() {
        sut = new FarmResource();
        farmService = mock(FarmServiceImp.class);
        accountService = mock(AccountServiceImp.class);

        farmDTO = new FarmDTO();
        userDTO = new UserDTO();
        tokenDTO = new TokenDTO(userDTO,"1234");

        when(farmService.createFarm(userDTO)).thenReturn(farmDTO);
        when(accountService.verifyToken(tokenDTO.getToken())).thenReturn(userDTO);
        when(farmService.getFarm(userDTO)).thenReturn(farmDTO);

        sut.setAccountService(accountService);
        sut.setFarmService(farmService);
    }

    @Test
    void createFarmReturnsCorrectResponse() {
        Response expected = Response.status(201)
                .entity(farmService.createFarm(userDTO))
                .build();
        Response result = sut.createNewFarm(tokenDTO.getToken());

        assertEquals(expected.toString(),result.toString());
    }

    @Test
    void getFarmCallsVerifyToken(){
        sut.getFarm(tokenDTO.getToken());
        verify(accountService, times(1)).verifyToken(tokenDTO.getToken());
    }

    @Test
    void getFarmCallsGetFarm(){
        sut.getFarm(tokenDTO.getToken());
        verify(farmService, times(1)).getFarm(userDTO);
    }
}