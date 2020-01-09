package nl.han.oose.sapporo.resource;

import nl.han.oose.sapporo.dto.*;
import nl.han.oose.sapporo.service.IAccountService;
import nl.han.oose.sapporo.service.IPlotService;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import javax.ws.rs.core.Response;
import java.util.ArrayList;

public class PlotResourceTest {
    private PlotResource sut = new PlotResource();
    private final int PLOT_ID = 1;
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
        Mockito.when(plotService.placePlant(PLANT, PLOT_ID, USER)).thenReturn(PLOT);
        Mockito.when(plotService.harvestPlant(PLOT, USER, PLOT_ID)).thenReturn(PLOT);
        Mockito.when(plotService.purchasePlot(PLOT_ID, USER)).thenReturn(ALL_PLOTS);
        Mockito.when(plotService.editWater(USER, PLOT_ID, 10, false)).thenReturn(PLOT);
        Mockito.when(plotService.placeAnimal(animal, PLOT_ID, USER)).thenReturn(ALL_PLOTS);
        Mockito.when(plotService.sellProduct(PLOT, USER, PLOT_ID)).thenReturn(ALL_PLOTS);
        Mockito.when(plotService.changeStatus(10,"Normal")).thenReturn(PLOT);
    }

    @Test
    public void placePlantsCallsAuthenticateByToken() {
        sut.placePlantOnPlot(TOKEN, PLOT_ID, PLANT);
        Mockito.verify(accountService, Mockito.times(1)).verifyToken(TOKEN);
    }

    @Test
    public void placePlantsCallsPlacePlant() {
        sut.placePlantOnPlot(TOKEN, PLOT_ID, PLANT);
        Mockito.verify(plotService, Mockito.times(1)).placePlant(PLANT, PLOT_ID, USER);
    }

    @Test
    public void placePlantsReturnsRightPlot() {
        sut.placePlantOnPlot(TOKEN, PLOT_ID, PLANT);
        Assert.assertEquals(PLOT, sut.placePlantOnPlot(TOKEN, PLOT_ID, PLANT).getEntity());
    }

    @Test
    public void harvestPlantFromPlotCallsAuthenticateByToken() {
        sut.harvestPlantFromPlot(TOKEN, PLOT_ID, PLOT);
        Mockito.verify(accountService, Mockito.times(1)).verifyToken(TOKEN);
    }

    @Test
    public void harvestPlantFromPlotCallsHarvestPlot() {
        sut.harvestPlantFromPlot(TOKEN, PLOT_ID, PLOT);
        Mockito.verify(plotService, Mockito.times(1)).harvestPlant(PLOT, USER, PLOT_ID);
    }

    @Test
    public void harvestPlantFromPlotReturnsRightPlot() {
        Assert.assertEquals(PLOT, sut.harvestPlantFromPlot(TOKEN, PLOT_ID, PLOT).getEntity());
    }

    @Test
    public void updateAgeCallsUpdateAge() {
        sut.updateAge(TOKEN,PLOT_ID,1000);
        Mockito.verify(plotService, Mockito.times(1)).updateAge(PLOT_ID,1000);
    }

    @Test
    public void purchasePlotCallsAuthenticateByToken() {
        sut.purchasePlot(TOKEN, PLOT_ID);
        Mockito.verify(accountService, Mockito.times(1)).verifyToken(TOKEN);
    }

    @Test
    public void purchasePlotCallsPurchasePlot() {
        sut.purchasePlot(TOKEN, PLOT_ID);
        Mockito.verify(plotService, Mockito.times(1)).purchasePlot(PLOT_ID, USER);
    }

    @Test
    public void purchasePlotReturnsAllPlots() {
        sut.purchasePlot(TOKEN, PLOT_ID);
        Assert.assertEquals(ALL_PLOTS, sut.purchasePlot(TOKEN, PLOT_ID).getEntity());
    }

    @Test
    public void waterPlantFromPlotCallsAuthenticateByToken() {
        sut.editWaterAmountForPlot(TOKEN, PLOT_ID, 10, false);
        Mockito.verify(accountService, Mockito.times(1)).verifyToken(TOKEN);
    }

    @Test
    public void waterPlantFromPlotCallsWaterPlot() {
        sut.editWaterAmountForPlot(TOKEN, PLOT_ID, 10, false);
        Mockito.verify(plotService, Mockito.times(1)).editWater(USER, PLOT_ID,10, false);
    }

    @Test
    public void waterPlantFromPlotReturnsRightPlot() {
        Assert.assertEquals(PLOT, sut.editWaterAmountForPlot(TOKEN, PLOT_ID,10, false).getEntity());
    }

    @Test
    public void waterPlantReturnsRightResponse() {
        Response excpected = Response.status(Response.Status.OK)
                .entity(PLOT)
                .build();
        Response result = sut.changeStatus(TOKEN,10,"Normal");

        Assert.assertEquals(excpected.toString(),result.toString());
    }

    public void placeAnimalCallsAuthenticateByToken() {
        sut.placeAnimalOnPlot(TOKEN, PLOT_ID, animal);
        Mockito.verify(accountService, Mockito.times(1)).verifyToken(TOKEN);
    }

    @Test
    public void placeAnimalCallsPlaceAnimal() {
        sut.placeAnimalOnPlot(TOKEN, PLOT_ID, animal);
        Mockito.verify(plotService, Mockito.times(1)).placeAnimal(animal, PLOT_ID, USER);
    }

    @Test
    public void placeAnimalReturnsAllPlots() {
        sut.placeAnimalOnPlot(TOKEN, PLOT_ID, animal);
        Assert.assertEquals(ALL_PLOTS, sut.placeAnimalOnPlot(TOKEN, PLOT_ID, animal).getEntity());
    }

    @Test
    public void sellAnimalProductCallsAuthenticateByToken() {
        sut.sellAnimalProduct(TOKEN, PLOT_ID, PLOT);
        Mockito.verify(accountService, Mockito.times(1)).verifyToken(TOKEN);
    }

    @Test
    public void sellAnimalProductCallsSellProduct() {
        sut.sellAnimalProduct(TOKEN, PLOT_ID, PLOT);
        Mockito.verify(plotService, Mockito.times(1)).sellProduct(PLOT, USER, PLOT_ID);
    }

    @Test
    public void sellAnimalProductReturnsAllPlots() {
        sut.sellAnimalProduct(TOKEN, PLOT_ID, PLOT);
        Assert.assertEquals(ALL_PLOTS, sut.sellAnimalProduct(TOKEN, PLOT_ID, PLOT).getEntity());
    }

    @Test
    public void clearPlotCallsClearPlot() {
        sut.clearPlot(TOKEN,PLOT_ID);
        Mockito.verify(plotService,Mockito.times(1)).clearPlot(USER, PLOT_ID);
    }
}
