package nl.han.oose.sapporo.service;

import nl.han.oose.sapporo.dto.FarmDTO;
import nl.han.oose.sapporo.dto.UserDTO;
import nl.han.oose.sapporo.persistence.FarmDAOImp;
import nl.han.oose.sapporo.persistence.IFarmDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.*;

class FarmServiceImpTest {
    private IFarmService sut;
    private IFarmDAO farmDAO;
    private UserDTO userDTO;
    private FarmDTO farmDTO;

    @BeforeEach
    void setup() {
        sut = new FarmServiceImp();

        userDTO = new UserDTO();
        farmDTO = new FarmDTO(5,3);

        farmDAO = mock(FarmDAOImp.class);
        sut.setFarm(farmDAO);
        when(farmDAO.createFarm(anyObject(),anyObject())).thenReturn(farmDTO);
    }

    @Test
    void createFarmCallsCheckUser() {
        sut.createFarm(userDTO);
        verify(farmDAO, Mockito.times(1)).checkIfUserHasAFarm(userDTO);
    }

    @Test
    void createFarmReturnsFarm() {
        FarmDTO result = sut.createFarm(userDTO);
        assertEquals(farmDTO,result);
    }
}