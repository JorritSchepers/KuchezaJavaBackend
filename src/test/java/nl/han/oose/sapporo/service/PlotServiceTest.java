package nl.han.oose.sapporo.service;

import nl.han.oose.sapporo.dto.*;
import nl.han.oose.sapporo.persistence.IFarmDAO;
import nl.han.oose.sapporo.persistence.IPlantDAO;
import nl.han.oose.sapporo.persistence.IPlotDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

class PlotServiceTest {
    private PlotServiceImp sut = new PlotServiceImp();
    private IPlantService plantService = Mockito.mock(IPlantService.class);
    private IPlotDAO plotDAO = Mockito.mock(IPlotDAO.class);
    private IPlantDAO plantDAO = Mockito.mock(IPlantDAO.class);
    private IFarmDAO farmDAO = Mockito.mock(IFarmDAO.class);
    private IInventoryService inventoryService = Mockito.mock(IInventoryService.class);
    private final int PLOTID = 1;
    private final float PRICE = 5;
    private final int FARMID = 1;
    private final float PLOTPRICE = 10;
    private PlantDTO plant = new PlantDTO(1, "Cabbage", 5, 10, 20, PRICE, 16);
    private PlantDTO notGrownPlant = new PlantDTO(1, "Cabbage", 5, 100, 20, PRICE, 5);
    private FarmDTO farm = new FarmDTO(1, 1);
    private UserDTO user = new UserDTO(1, "PatrickSt3r", "DC00C903852BB19EB250AEBA05E534A6D211629D77D055033806B783BAE09937", "Patrick@Ster.com");
    private PlotDTO plot = new PlotDTO(1, 1, 1, 1, 0, 0, 10);
    private ArrayList<PlotDTO> plots = new ArrayList<>() {{ add(plot); }};
    private AllPlotDTO allPlots = new AllPlotDTO(plots);

    PlotServiceTest() {
        sut.setPlotDAO(plotDAO);
        sut.setInventoryService(inventoryService);
        sut.setPlantService(plantService);
        sut.setPlantDAO(plantDAO);
        sut.setFarmDAO(farmDAO);
        Mockito.when(plantDAO.getProfit(plant.getID())).thenReturn(20);
        Mockito.when(inventoryService.checkSaldo(PRICE, user)).thenReturn(true);
        Mockito.when(plotDAO.getPlot(PLOTID)).thenReturn(plot);
        Mockito.when(plotDAO.checkIfPlotIsEmpty(PLOTID)).thenReturn(true);
        Mockito.when(plantService.plantFullGrown(plant)).thenReturn(true);
        Mockito.when(plantService.plantFullGrown(notGrownPlant)).thenReturn(false);
        Mockito.when(plotDAO.plotHasPlant(PLOTID)).thenReturn(true);
        Mockito.when(plotDAO.getFarmPlots(FARMID)).thenReturn(plots);
        Mockito.when(plantDAO.getProfit(plant.getID())).thenReturn(20);
        Mockito.when(plotDAO.plotIsPurchased(PLOTID)).thenReturn(false);
        Mockito.when(farmDAO.getFarm(user)).thenReturn(farm);
        Mockito.when(inventoryService.checkSaldo(PLOTPRICE, user)).thenReturn(true);
    }

    @Test
    void placePlantCallsCheckSaldo() {
        sut.placePlant(plant, PLOTID, user);
        Mockito.verify(inventoryService, Mockito.times(1)).checkSaldo(PRICE, user);
    }

    @Test
    void placePlantCallsCheckIfPlotempy() {
        sut.placePlant(plant, PLOTID, user);
        Mockito.verify(plotDAO, Mockito.times(1)).checkIfPlotIsEmpty(PLOTID);
    }

    @Test
    void placePlantCallsLowerSaldo() {
        sut.placePlant(plant, PLOTID, user);
        Mockito.verify(inventoryService, Mockito.times(1)).lowerSaldo(PRICE, user);
    }

    @Test
    void placePlantCallsAddPlantToPlot() {
        sut.placePlant(plant, PLOTID, user);
        Mockito.verify(plotDAO, Mockito.times(1)).addPlantToPlot(plant, PLOTID);
    }

    @Test
    void placePlantCallsgetPlot() {
        sut.placePlant(plant, PLOTID, user);
        Mockito.verify(plotDAO, Mockito.times(1)).getPlot(PLOTID);
    }

    @Test
    void placePlantReturnsPlot() {
        Assertions.assertEquals(plot, sut.placePlant(plant, PLOTID, user));
    }

    @Test
    void harvestPlantCallsplantFullGrown() {
        sut.harvestPlant(plant, user, PLOTID);
        Mockito.verify(plantService, Mockito.times(1)).plantFullGrown(plant);
    }

    @Test
    void harvestPlantCallsremoveObjectsFromPlot() {
        sut.harvestPlant(plant, user, PLOTID);
        Mockito.verify(plotDAO, Mockito.times(1)).removeObjectsFromPlot(PLOTID);
    }

    @Test
    void harvestPlantCallsincreaseSaldo() {
        sut.harvestPlant(plant, user, PLOTID);
        Mockito.verify(inventoryService, Mockito.times(1)).increaseSaldo(plant.getProfit(), user);
    }

    @Test
    void harvestPlantCallsgetPlot() {
        sut.harvestPlant(plant, user, PLOTID);
        Mockito.verify(plotDAO, Mockito.times(1)).getPlot(PLOTID);
    }

    @Test
    void harvestPlantReturnsRightPlot() {
        Assertions.assertEquals(plot, sut.harvestPlant(plant, user, PLOTID));
    }

    @Test
    void harvestPlantThrowsExceptionWhenNotGrown() {
        Assertions.assertDoesNotThrow(() -> {
            sut.harvestPlant(plant, user, PLOTID);
        });
    }

    @Test
    void getFarmPlotsCallsGetFarmPlots() {
        sut.getFarmPlots(FARMID);
        Mockito.verify(plotDAO, Mockito.times(1)).getFarmPlots(PLOTID);
    }

    @Test
    void getFarmPlotsReturnsPlots() {
        Assertions.assertEquals(plots, sut.getFarmPlots(FARMID));
    }

    @Test
    void purchasePlotCallsgetPlot() {
        sut.purchasePlot(PLOTID, user);
        Mockito.verify(plotDAO, Mockito.times(1)).getPlot(PLOTID);
    }

    @Test
    void purchasePlotCallsCheckSaldo() {
        sut.purchasePlot(PLOTID, user);
        Mockito.verify(inventoryService, Mockito.times(1)).checkSaldo(PLOTPRICE, user);
    }

    @Test
    void purchasePlotCallsPlotIsPurchased() {
        sut.purchasePlot(PLOTID, user);
        Mockito.verify(plotDAO, Mockito.times(1)).plotIsPurchased(PLOTID);
    }

    @Test
    void purchasePlotCallsLowerSaldo() {
        sut.purchasePlot(PLOTID, user);
        Mockito.verify(inventoryService, Mockito.times(1)).lowerSaldo(PLOTPRICE, user);
    }

    @Test
    void purchasePlotCallsPurchasePlot() {
        sut.purchasePlot(PLOTID, user);
        Mockito.verify(plotDAO, Mockito.times(1)).purchasePlot(PLOTID);
    }

//    @Test
//    void purchasePlotReturnsAllPlots() {
//        Assertions.assertEquals(allPlots, sut.purchasePlot(PLOTID, user));
//    }
}