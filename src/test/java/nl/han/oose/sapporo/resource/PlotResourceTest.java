package nl.han.oose.sapporo.resource;

import nl.han.oose.sapporo.dto.PlantDTO;
import nl.han.oose.sapporo.dto.PlotDTO;
import nl.han.oose.sapporo.dto.TokenDTO;
import nl.han.oose.sapporo.dto.UserDTO;
import nl.han.oose.sapporo.service.IAccountService;
import nl.han.oose.sapporo.service.IPlotService;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class PlotResourceTest {
    private PlotResource sut = new PlotResource();
    private String token = "123456890";
    private IAccountService accountService = Mockito.mock(IAccountService.class);
    private IPlotService plotService = Mockito.mock(IPlotService.class);
    private PlantDTO plant = new PlantDTO();
    private UserDTO user = new UserDTO();
    private PlotDTO plot = new PlotDTO(1,1,1,1,0,0,0);

    public PlotResourceTest(){
        sut.setAccountService(accountService);
        sut.setPlotService(plotService);
        Mockito.when(accountService.authenticateByToken(token)).thenReturn(user);
        Mockito.when(plotService.placePlant(plant,1,user)).thenReturn(plot);
    }

    @Test
    public void placePlantsCallsAuthenticateByToken() {
        sut.placePlant(token, 1,plant);
        Mockito.verify(accountService, Mockito.times(1)).authenticateByToken(token);
    }

    @Test
    public void placePlantsCallsPlacePlant() {
        sut.placePlant(token, 1,plant);
        Mockito.verify(plotService, Mockito.times(1)).placePlant(plant,1,user);
    }

    @Test
    public void placePlantsReturnsRightPlot() {
        Assert.assertEquals(plot,sut.placePlant(token, 1,plant).getEntity());
    }
}