package nl.han.oose.sapporo.service;

import nl.han.oose.sapporo.dto.*;
import nl.han.oose.sapporo.persistence.IFarmDAO;
import nl.han.oose.sapporo.persistence.IPlantDAO;
import nl.han.oose.sapporo.persistence.IPlotDAO;
import nl.han.oose.sapporo.persistence.PlantDAOImp;
import nl.han.oose.sapporo.service.exception.PlotIsAlreadyPurchasedException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

class PlotServiceTest {
    private PlotServiceImp sut = new PlotServiceImp();
    private IActionService actionService = Mockito.mock(IActionService.class);
    private IPlantService plantService = Mockito.mock(IPlantService.class);
    private IPlotDAO plotDAO = Mockito.mock(IPlotDAO.class);
    private IPlantDAO plantDAO = Mockito.mock(PlantDAOImp.class);
    private IFarmDAO farmDAO = Mockito.mock(IFarmDAO.class);
    private IInventoryService inventoryService = Mockito.mock(IInventoryService.class);
    private final int PLOTID = 1;
    private final float PRICE = 5;
    private final int FARMID = 1;
    private final float PLOTPRICE = 10;
    private final int WATER = 10;
    private PlantDTO plant = new PlantDTO(1, "Cabbage", 5, 10, 20, PRICE);
    private FarmDTO farm = new FarmDTO(1, 1);
    private UserDTO user = new UserDTO(1, "PatrickSt3r", "DC00C903852BB19EB250AEBA05E534A6D211629D77D055033806B783BAE09937", "Patrick@Ster.com");
    private PlotDTO plot = new PlotDTO(1, 1, 1, 1, 0, 0, 10);
    private PlotDTO plotWithGrownPlant = new PlotDTO(1, 1, 1, 1, 0, 1, 0, 100);
    private PlotDTO plotWithoutGrownPlant = new PlotDTO(2, 1, 1, 1, 0, 1, 0, 0);

    private ArrayList<PlotDTO> plots = new ArrayList<>() {{ add(plot); }};
    private AllPlotDTO allPlots = new AllPlotDTO(plots);

    PlotServiceTest() {
        sut.setActionService(actionService);
        sut.setPlotDAO(plotDAO);
        sut.setInventoryService(inventoryService);
        sut.setPlantService(plantService);
        sut.setPlantDAO(plantDAO);
        sut.setFarmDAO(farmDAO);
        Mockito.when(plantDAO.getProfit(plant.getID())).thenReturn(20);
        Mockito.when(inventoryService.checkIfPlayerHasEnoughSaldo(PRICE, user)).thenReturn(true);
        Mockito.when(plotDAO.getPlot(PLOTID)).thenReturn(plotWithGrownPlant);
        Mockito.when(inventoryService.checkIfPlayerHasEnoughWater(10, user)).thenReturn(true);
        Mockito.when(plotDAO.checkIfPlotIsEmpty(PLOTID)).thenReturn(true);
        Mockito.when(plantDAO.getProfit(Mockito.anyInt())).thenReturn(10);
        Mockito.when(plantService.plantFullGrown(plotWithGrownPlant)).thenReturn(true);
        Mockito.when(plantService.plantFullGrown(plotWithoutGrownPlant)).thenReturn(false);
        Mockito.when(plotDAO.plotHasPlant(PLOTID)).thenReturn(true);
        Mockito.when(plotDAO.getFarmPlots(FARMID)).thenReturn(plots);
        Mockito.when(plantDAO.getProfit(plant.getID())).thenReturn(20);
        Mockito.when(plotDAO.plotIsPurchased(PLOTID)).thenReturn(false);
        Mockito.when(farmDAO.getFarm(user)).thenReturn(farm);
        Mockito.when(inventoryService.checkIfPlayerHasEnoughSaldo(0, user)).thenReturn(true);
    }

    @Test
    void placePlantCallsCheckSaldo() {
        sut.placePlant(plant, PLOTID, user);
        Mockito.verify(inventoryService, Mockito.times(1)).checkIfPlayerHasEnoughSaldo(PRICE, user);
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
        Assertions.assertEquals(plotWithGrownPlant, sut.placePlant(plant, PLOTID, user));
    }

    @Test
    void harvestPlantCallsplantFullGrown() {
        sut.harvestPlant(plotWithGrownPlant, user, PLOTID);
        Mockito.verify(plantService, Mockito.times(1)).plantFullGrown(plotWithGrownPlant);
    }

    @Test
    void harvestPlantCallsremoveObjectsFromPlot() {
        sut.harvestPlant(plotWithGrownPlant, user, PLOTID);
        Mockito.verify(plotDAO, Mockito.times(1)).removeObjectsFromPlot(PLOTID);
    }

    @Test
    void harvestPlantCallsincreaseSaldo() {
        sut.harvestPlant(plotWithGrownPlant, user, PLOTID);
        Mockito.verify(inventoryService, Mockito.times(1)).increaseSaldo(plant.getProfit(), user);
    }

    @Test
    void harvestPlantCallsgetPlot() {
        sut.harvestPlant(plotWithGrownPlant, user, PLOTID);
        Mockito.verify(plotDAO, Mockito.times(2)).getPlot(PLOTID);
    }

    @Test
    void harvestPlantReturnsRightPlot() {
        Assertions.assertEquals(plotWithGrownPlant, sut.harvestPlant(plotWithGrownPlant, user, PLOTID));
    }

    @Test
    void harvestPlantThrowsExceptionWhenNotGrown() {
        Assertions.assertDoesNotThrow(() -> {
            sut.harvestPlant(plotWithoutGrownPlant, user, PLOTID);
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
    void updateAgeCallsUpdateAge() {
        sut.updageAge(1,1000);
        Mockito.verify(plotDAO, Mockito.times(1)).updateAge(1,1000);
    }

    @Test
    void purchasePlotCallsPlotIsPurchased() {
        sut.purchasePlot(PLOTID, user);
        Mockito.verify(plotDAO, Mockito.times(2)).plotIsPurchased(PLOTID);
    }

    @Test
    void purchasePlotCallsgetPlot() {
        sut.purchasePlot(PLOTID, user);
        Mockito.verify(plotDAO, Mockito.times(1)).getPlot(PLOTID);
    }

    @Test
    void waterPlantCallsCheckWater(){
        sut.editWater(user, PLOTID, WATER);
        Mockito.verify(inventoryService, Mockito.times(1)).checkIfPlayerHasEnoughWater(WATER, user);
    }

    @Test
    void waterPlantCallsLowerWater(){
        sut.editWater(user, PLOTID, WATER);
        Mockito.verify(inventoryService, Mockito.times(1)).lowerWater(WATER, user);
    }

    @Test
    void waterPlantCallsIncreaseWater(){
        sut.editWater(user, PLOTID, WATER);
        Mockito.verify(plotDAO, Mockito.times(1)).editWaterAvailable(WATER, PLOTID);
    }

    @Test
    void waterPlantCallsGetPlot(){
        sut.editWater(user, PLOTID, WATER);
        Mockito.verify(plotDAO, Mockito.times(2)).getPlot(PLOTID);
    }

    @Test
    void waterPlantCallsPlotHasPlant(){
        sut.editWater(user, PLOTID, WATER);
        Mockito.verify(plotDAO, Mockito.times(1)).plotHasPlant(PLOTID);
    }

    @Test
    void waterPlantReturnsPlot(){ Assertions.assertTrue(plotWithGrownPlant.equals(sut.editWater(user, PLOTID, WATER)));}

    @Test
    void purchasePlotReturnsAllPlots() {
        Assertions.assertEquals(allPlots.getPlots().size(), sut.purchasePlot(PLOTID, user).getPlots().size());
    }

    @Test
    void purchasePlotCallsGetFarm() {
        sut.purchasePlot(PLOTID, user);
        Mockito.verify(farmDAO, Mockito.times(1)).getFarm(user);
    }

    @Test
    void purchasePlotThrowsExceptionWhenPlotIsAlreadyPurchased() {
        Mockito.when(plotDAO.plotIsPurchased(PLOTID)).thenReturn(true);
        Assertions.assertThrows(PlotIsAlreadyPurchasedException.class, () -> { sut.purchasePlot(PLOTID, user); });
    }
}