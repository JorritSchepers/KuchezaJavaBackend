package nl.han.oose.sapporo.resource;

import nl.han.oose.sapporo.dto.*;
import nl.han.oose.sapporo.service.IAccountService;
import nl.han.oose.sapporo.service.IPlotService;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.ws.rs.core.Response;
import java.util.ArrayList;

public class PlotResourceTest {
    private PlotResource sut = new PlotResource();
    private final String TOKEN = "123456890";
    private IAccountService accountService = Mockito.mock(IAccountService.class);
    private IPlotService plotService = Mockito.mock(IPlotService.class);
    private PlantDTO PLANT = new PlantDTO();
    private UserDTO USER = new UserDTO();
    private PlotDTO PLOT = new PlotDTO(1, 1, 1, 1, 0, 0, 0);
    private ArrayList<PlotDTO> plots = new ArrayList<>() {{ add(PLOT); }};
    private AllPlotDTO ALL_PLOTS = new AllPlotDTO(plots);
    private AnimalDTO animal = new AnimalDTO();

    public PlotResourceTest() {
        sut.setAccountService(accountService);
        sut.setPlotService(plotService);
        Mockito.when(accountService.verifyToken(TOKEN)).thenReturn(USER);
        Mockito.when(plotService.placePlant(PLANT, 1, USER)).thenReturn(PLOT);
        Mockito.when(plotService.harvestPlant(PLOT, USER, 1)).thenReturn(PLOT);
        Mockito.when(plotService.purchasePlot(1, USER)).thenReturn(ALL_PLOTS);
        Mockito.when(plotService.editWater(USER, 1, 10)).thenReturn(PLOT);
        Mockito.when(plotService.placeAnimal(animal, 1, USER)).thenReturn(ALL_PLOTS);
        Mockito.when(plotService.changeStatus(10,"Normal")).thenReturn(PLOT);
    }

    @Test
    public void placePlantsCallsAuthenticateByToken() {
        sut.placePlantOnPlot(TOKEN, 1, PLANT);
        Mockito.verify(accountService, Mockito.times(1)).verifyToken(TOKEN);
    }

    @Test
    public void placePlantsCallsPlacePlant() {
        sut.placePlantOnPlot(TOKEN, 1, PLANT);
        Mockito.verify(plotService, Mockito.times(1)).placePlant(PLANT, 1, USER);
    }

    @Test
    public void placePlantsReturnsRightPlot() {
        sut.placePlantOnPlot(TOKEN, 1, PLANT);
        Assert.assertEquals(PLOT, sut.placePlantOnPlot(TOKEN, 1, PLANT).getEntity());
    }

    @Test
    public void harvestPlantFromPlotCallsAuthenticateByToken() {
        sut.harvestPlantFromPlot(TOKEN, 1, PLOT);
        Mockito.verify(accountService, Mockito.times(1)).verifyToken(TOKEN);
    }

    @Test
    public void harvestPlantFromPlotCallsHarvesPlot() {
        sut.harvestPlantFromPlot(TOKEN, 1, PLOT);
        Mockito.verify(plotService, Mockito.times(1)).harvestPlant(PLOT, USER, 1);
    }

    @Test
    public void harvestPlantFromPlotReturnsRightPlot() {
        Assert.assertEquals(PLOT, sut.harvestPlantFromPlot(TOKEN, 1, PLOT).getEntity());
    }

    @Test
    public void updateAgeCallsUpdateAge() {
        sut.updateAge(TOKEN,1,1000);
        Mockito.verify(plotService, Mockito.times(1)).updateAge(1,1000);
    }

    @Test
    public void purchasePlotCallsAuthenticateByToken() {
        sut.purchasePlot(TOKEN, 1);
        Mockito.verify(accountService, Mockito.times(1)).verifyToken(TOKEN);
    }

    @Test
    public void purchasePlotCallsPurchasePlot() {
        sut.purchasePlot(TOKEN, 1);
        Mockito.verify(plotService, Mockito.times(1)).purchasePlot(1, USER);
    }

    @Test
    public void purchasePlotReturnsAllPlots() {
        sut.purchasePlot(TOKEN, 1);
        Assert.assertEquals(ALL_PLOTS, sut.purchasePlot(TOKEN, 1).getEntity());
    }

    @Test
    public void waterPlantFromPlotCallsAuthenticateByToken() {
        sut.editWaterAmountForPlot(TOKEN, 1, 10);
        Mockito.verify(accountService, Mockito.times(1)).verifyToken(TOKEN);
    }

    @Test
    public void waterPlantFromPlotCallsWaterPlot() {
        sut.editWaterAmountForPlot(TOKEN, 1, 10);
        Mockito.verify(plotService, Mockito.times(1)).editWater(USER, 1,10);
    }

    @Test
    public void waterPlantFromPlotReturnsRightPlot() {
        Assert.assertEquals(PLOT, sut.editWaterAmountForPlot(TOKEN, 1,10).getEntity());
    }

    @Test
    public void waterPlantReturnsRightResponse() {
        Response excpected = Response.status(Response.Status.OK)
                .entity(PLOT)
                .build();
        Response result = sut.changeStatus(token,10,"Normal");

        Assert.assertEquals(excpected.toString(),result.toString());
    }

    public void placeAnimalCallsAuthenticateByToken() {
        sut.placeAnimalOnPlot(TOKEN, 1, animal);
        Mockito.verify(accountService, Mockito.times(1)).verifyToken(TOKEN);
    }

    @Test
    public void placeAnimalCallsPlaceAnimal() {
        sut.placeAnimalOnPlot(TOKEN, 1, animal);
        Mockito.verify(plotService, Mockito.times(1)).placeAnimal(animal, 1, USER);
    }

    @Test
    public void placeAnimalReturnsAllPlots() {
        sut.placeAnimalOnPlot(TOKEN, 1, animal);
        Assert.assertEquals(ALL_PLOTS, sut.placeAnimalOnPlot(TOKEN, 1, animal).getEntity());
    }
}
