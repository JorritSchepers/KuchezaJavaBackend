package nl.han.oose.sapporo.service;

import nl.han.oose.sapporo.dto.FarmDTO;
import nl.han.oose.sapporo.dto.UserDTO;
import nl.han.oose.sapporo.persistence.IFarmDAO;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

public class FarmServiceTest {
    private FarmServiceImp sut = new FarmServiceImp();
    private IFarmDAO farmDAO;
    private IPlotService plotService;
    private UserDTO user = new UserDTO(1, "test", "testwachtwoord", "Test@gmail.com");

    FarmServiceTest() {
        farmDAO = Mockito.mock(IFarmDAO.class);
        plotService = Mockito.mock(IPlotService.class);
        Mockito.when(farmDAO.getFarm(user)).thenReturn(new FarmDTO(1, 1));
        Mockito.when(plotService.getFarmPlots(1)).thenReturn(new ArrayList<>());
        sut.setFarmDAO(farmDAO);
        sut.setPlotService(plotService);
    }

    @Test
    void getFarmPlotsCallsGetFarm() {
        sut.getFarm(user);
        Mockito.verify(farmDAO, Mockito.times(1)).getFarm(user);
    }

    @Test
    void getFarmPlotsCallsGetFarmPlots() {
        sut.getFarm(user);
        Mockito.verify(plotService, Mockito.times(1)).getFarmPlots(user.getId());
    }
}
