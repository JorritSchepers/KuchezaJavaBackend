package nl.han.oose.sapporo.resource;

import nl.han.oose.sapporo.dto.AllPlotDTO;
import nl.han.oose.sapporo.dto.PlantDTO;
import nl.han.oose.sapporo.dto.PlotDTO;
import nl.han.oose.sapporo.dto.UserDTO;
import nl.han.oose.sapporo.service.IAccountService;
import nl.han.oose.sapporo.service.IPlotService;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

public class PlotResourceTest {
    private PlotResource sut = new PlotResource();
    private String token = "123456890";
    private IAccountService accountService = Mockito.mock(IAccountService.class);
    private IPlotService plotService = Mockito.mock(IPlotService.class);
    private PlantDTO plant = new PlantDTO();
    private UserDTO user = new UserDTO();
    private PlotDTO plot = new PlotDTO(1, 1, 1, 1, 0, 0, 0);
    private ArrayList<PlotDTO> plots = new ArrayList<>() {{ add(plot); }};
    private AllPlotDTO allPlots = new AllPlotDTO(plots);

    public PlotResourceTest() {
        sut.setAccountService(accountService);
        sut.setPlotService(plotService);
        Mockito.when(accountService.verifyToken(token)).thenReturn(user);
        Mockito.when(plotService.placePlant(plant, 1, user)).thenReturn(plot);
        Mockito.when(plotService.harvestPlant(plot, user, 1)).thenReturn(plot);
        Mockito.when(plotService.purchasePlot(1, user)).thenReturn(allPlots);
        Mockito.when(plotService.editWater(user, 1, 10)).thenReturn(plot);
    }

    @Test
    public void placePlantsCallsAuthenticateByToken() {
        sut.placePlantOnPlot(token, 1, plant);
        Mockito.verify(accountService, Mockito.times(1)).verifyToken(token);
    }

    @Test
    public void placePlantsCallsPlacePlant() {
        sut.placePlantOnPlot(token, 1, plant);
        Mockito.verify(plotService, Mockito.times(1)).placePlant(plant, 1, user);
    }

    @Test
    public void placePlantsReturnsRightPlot() {
        sut.placePlantOnPlot(token, 1, plant);
        Assert.assertEquals(plot, sut.placePlantOnPlot(token, 1, plant).getEntity());
    }

    @Test
    public void harvestPlantFromPlotCallsAuthenticateByToken() {
        sut.harvestPlantFromPlot(token, 1, plot);
        Mockito.verify(accountService, Mockito.times(1)).verifyToken(token);
    }

    @Test
    public void harvestPlantFromPlotCallsHarvesPlot() {
        sut.harvestPlantFromPlot(token, 1, plot);
        Mockito.verify(plotService, Mockito.times(1)).harvestPlant(plot, user, 1);
    }

    @Test
    public void harvestPlantFromPlotReturnsRightPlot() {
        Assert.assertEquals(plot, sut.harvestPlantFromPlot(token, 1, plot).getEntity());
    }

    @Test
    public void updateAgeCallsUpdateAge() {
        sut.updateAge(token,1,1000);
        Mockito.verify(plotService, Mockito.times(1)).updageAge(1,1000);
    }

    @Test
    public void purchasePlotCallsAuthenticateByToken() {
        sut.purchasePlot(token, 1);
        Mockito.verify(accountService, Mockito.times(1)).verifyToken(token);
    }

    @Test
    public void purchasePlotCallsPurchasePlot() {
        sut.purchasePlot(token, 1);
        Mockito.verify(plotService, Mockito.times(1)).purchasePlot(1, user);
    }

    @Test
    public void purchasePlotReturnsAllPlots() {
        sut.purchasePlot(token, 1);
        Assert.assertEquals(allPlots, sut.purchasePlot(token, 1).getEntity());
    }

    @Test
    public void waterPlantFromPlotCallsAuthenticateByToken() {
        sut.editWaterAmountForPlot(token, 1, 10);
        Mockito.verify(accountService, Mockito.times(1)).verifyToken(token);
    }

    @Test
    public void waterPlantFromPlotCallsWaterPlot() {
        sut.editWaterAmountForPlot(token, 1, 10);
        Mockito.verify(plotService, Mockito.times(1)).editWater(user, 1,10);
    }

    @Test
    public void waterPlantFromPlotReturnsRightPlot() {
        Assert.assertEquals(plot, sut.editWaterAmountForPlot(token, 1,10).getEntity());
    }
}