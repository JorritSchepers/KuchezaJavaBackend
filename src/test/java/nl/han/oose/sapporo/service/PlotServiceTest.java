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
    private final int PLOT_ID = 1;
    private final float PRICE = 5;
    private final int FARM_ID = 1;
    private final int WATER = 10;
    private final int START_WATER = 25;
    private PlantDTO plant = new PlantDTO(1, "Cabbage", 5, 10, 20, PRICE);
    private FarmDTO farm = new FarmDTO(1, 1);
    private UserDTO user = new UserDTO(1, "PatrickSt3r", "DC00C903852BB19EB250AEBA05E534A6D211629D77D055033806B783BAE09937", "Patrick@Ster.com");
    private PlotDTO plot = new PlotDTO(1, 1, 1, 1, 0, 0, 10);
    private PlotDTO plotWithGrownPlant = new PlotDTO(1, 1, 1, 1, 0, 1, 0, 100);
    private PlotDTO plotWithoutGrownPlant = new PlotDTO(2, 1, 1, 1, 0, 1, 0, 0);
    private AnimalDTO animal = new AnimalDTO(1, "Cabbage", 10, 300, 10, 20, PRICE);

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
        Mockito.when(plotDAO.getPlot(PLOT_ID)).thenReturn(plotWithGrownPlant);
        Mockito.when(inventoryService.checkIfPlayerHasEnoughWater(10, user)).thenReturn(true);
        Mockito.when(plotDAO.checkIfPlotIsEmpty(PLOT_ID)).thenReturn(true);
        Mockito.when(plantDAO.getProfit(Mockito.anyInt())).thenReturn(10);
        Mockito.when(plantService.plantFullGrown(plotWithGrownPlant)).thenReturn(true);
        Mockito.when(plantService.plantFullGrown(plotWithoutGrownPlant)).thenReturn(false);
        Mockito.when(plotDAO.plotHasPlant(PLOT_ID)).thenReturn(true);
        Mockito.when(plotDAO.getFarmPlots(FARM_ID)).thenReturn(plots);
        Mockito.when(plantDAO.getProfit(plant.getID())).thenReturn(20);
        Mockito.when(plotDAO.plotIsPurchased(PLOT_ID)).thenReturn(false);
        Mockito.when(farmDAO.getFarm(user)).thenReturn(farm);
        Mockito.when(inventoryService.checkIfPlayerHasEnoughSaldo(0, user)).thenReturn(true);
    }

    @Test
    void placePlantCallsCheckSaldo() {
        sut.placePlant(plant, PLOT_ID, user);
        Mockito.verify(inventoryService, Mockito.times(1)).checkIfPlayerHasEnoughSaldo(PRICE, user);
    }

    @Test
    void placePlantCallsCheckIfPlotempy() {
        sut.placePlant(plant, PLOT_ID, user);
        Mockito.verify(plotDAO, Mockito.times(1)).checkIfPlotIsEmpty(PLOT_ID);
    }

    @Test
    void placePlantCallsLowerSaldo() {
        sut.placePlant(plant, PLOT_ID, user);
        Mockito.verify(inventoryService, Mockito.times(1)).lowerSaldo(PRICE, user);
    }

    @Test
    void placePlantCallsAddPlantToPlot() {
        sut.placePlant(plant, PLOT_ID, user);
        Mockito.verify(plotDAO, Mockito.times(1)).addPlantToPlot(plant, PLOT_ID);
    }

    @Test
    void placePlantCallsActionService() {
        final int PLANT_SEED_ACTION_ID = 1;
        sut.placePlant(plant, PLOT_ID, user);
        Mockito.verify(actionService, Mockito.times(1)).setAction(user,PLANT_SEED_ACTION_ID,plant.getName());
    }

    @Test
    void placePlantCallsGetPlot() {
        sut.placePlant(plant, PLOT_ID, user);
        Mockito.verify(plotDAO, Mockito.times(1)).getPlot(PLOT_ID);
    }

    @Test
    void placePlantReturnsPlot() {
        Assertions.assertEquals(plotWithGrownPlant, sut.placePlant(plant, PLOT_ID, user));
    }

    @Test
    void harvestPlantCallsPlantFullGrown() {
        sut.harvestPlant(plotWithGrownPlant, user, PLOT_ID);
        Mockito.verify(plantService, Mockito.times(1)).plantFullGrown(plotWithGrownPlant);
    }

    @Test
    void harvestPlantCallsRemoveObjectsFromPlot() {
        sut.harvestPlant(plotWithGrownPlant, user, PLOT_ID);
        Mockito.verify(plotDAO, Mockito.times(1)).removeObjectsFromPlot(PLOT_ID);
    }

    @Test
    void harvestPlantCallsIncreaseSaldo() {
        sut.harvestPlant(plotWithGrownPlant, user, PLOT_ID);
        Mockito.verify(inventoryService, Mockito.times(1)).increaseSaldo(plant.getProfit(), user);
    }

    @Test
    void harvestPlantCallsGetPlot() {
        sut.harvestPlant(plotWithGrownPlant, user, PLOT_ID);
        Mockito.verify(plotDAO, Mockito.times(2)).getPlot(PLOT_ID);
    }

    @Test
    void harvestPlantCallsActionService() {
        final int HARVEST_PLANT_ACTION_ID = 2;
        final String NAME = "orange";
        Mockito.when(plantDAO.getname(1)).thenReturn(NAME);
        sut.harvestPlant(plotWithGrownPlant, user, PLOT_ID);
        Mockito.verify(actionService, Mockito.times(1)).setAction(user,HARVEST_PLANT_ACTION_ID,NAME);
    }

    @Test
    void harvestPlantReturnsRightPlot() {
        Assertions.assertEquals(plotWithGrownPlant, sut.harvestPlant(plotWithGrownPlant, user, PLOT_ID));
    }

    @Test
    void harvestPlantThrowsExceptionWhenNotGrown() {
        Assertions.assertDoesNotThrow(() -> {
            sut.harvestPlant(plotWithoutGrownPlant, user, PLOT_ID);
        });
    }

    @Test
    void getFarmPlotsCallsGetFarmPlots() {
        sut.getFarmPlots(FARM_ID);
        Mockito.verify(plotDAO, Mockito.times(1)).getFarmPlots(PLOT_ID);
    }

    @Test
    void getFarmPlotsReturnsPlots() {
        Assertions.assertEquals(plots, sut.getFarmPlots(FARM_ID));
    }

    @Test
    void updateAgeCallsUpdateAge() {
        sut.updageAge(1,1000);
        Mockito.verify(plotDAO, Mockito.times(1)).updateAge(1,1000);
    }

    @Test
    void purchasePlotCallsPlotIsPurchased() {
        sut.purchasePlot(PLOT_ID, user);
        Mockito.verify(plotDAO, Mockito.times(2)).plotIsPurchased(PLOT_ID);
    }

    @Test
    void purchasePlotCallsGetPlot() {
        sut.purchasePlot(PLOT_ID, user);
        Mockito.verify(plotDAO, Mockito.times(1)).getPlot(PLOT_ID);
    }

    @Test
    void purchasePlotCallsActionService() {
        final int PURCHASE_PLOT_ACTION_ID = 4;
        sut.purchasePlot(PLOT_ID, user);
        Mockito.verify(actionService, Mockito.times(1)).setAction(user,PURCHASE_PLOT_ACTION_ID,null);
    }

    @Test
    void waterPlantCallsCheckWater(){
        sut.editWater(user, PLOT_ID, WATER);
        Mockito.verify(inventoryService, Mockito.times(1)).checkIfPlayerHasEnoughWater(WATER, user);
    }

    @Test
    void waterPlantCallsLowerWater(){
        sut.editWater(user, PLOT_ID, WATER);
        Mockito.verify(inventoryService, Mockito.times(1)).lowerWater(WATER, user);
    }

    @Test
    void waterPlantCallsIncreaseWater(){
        sut.editWater(user, PLOT_ID, WATER);
        Mockito.verify(plotDAO, Mockito.times(1)).editWaterAvailable(WATER, PLOT_ID);
    }

    @Test
    void waterPlantCallsGetPlot(){
        sut.editWater(user, PLOT_ID, WATER);
        Mockito.verify(plotDAO, Mockito.times(2)).getPlot(PLOT_ID);
    }

    @Test
    void waterPlantCallsPlotHasPlant(){
        sut.editWater(user, PLOT_ID, WATER);
        Mockito.verify(plotDAO, Mockito.times(1)).plotHasPlant(PLOT_ID);
    }

    @Test
    void waterPlantReturnsPlot(){
        Assertions.assertEquals(plotWithGrownPlant, sut.editWater(user, PLOT_ID, WATER));}

    @Test
    void purchasePlotReturnsAllPlots() {
        Assertions.assertEquals(allPlots.getPlots().size(), sut.purchasePlot(PLOT_ID, user).getPlots().size());
    }

    @Test
    void purchasePlotCallsGetFarm() {
        sut.purchasePlot(PLOT_ID, user);
        Mockito.verify(farmDAO, Mockito.times(1)).getFarm(user);
    }

    @Test
    void purchasePlotThrowsExceptionWhenPlotIsAlreadyPurchased() {
        Mockito.when(plotDAO.plotIsPurchased(PLOT_ID)).thenReturn(true);
        Assertions.assertThrows(PlotIsAlreadyPurchasedException.class, () -> { sut.purchasePlot(PLOT_ID, user); });
    }

    @Test
    void placeAnimalCallsCheckSaldo() {
        sut.placeAnimal(animal, PLOT_ID, user);
        Mockito.verify(inventoryService, Mockito.times(1)).checkIfPlayerHasEnoughSaldo(PRICE, user);
    }

    @Test
    void placeAnimalCallsCheckIfPlotIsEmpty() {
        sut.placeAnimal(animal, PLOT_ID, user);
        Mockito.verify(plotDAO, Mockito.times(1)).checkIfPlotIsEmpty(PLOT_ID);
    }

    @Test
    void placeAnimalCallsLowerSaldo() {
        sut.placeAnimal(animal, PLOT_ID, user);
        Mockito.verify(inventoryService, Mockito.times(1)).lowerSaldo(PRICE, user);
    }

    @Test
    void placeAnimalCallsLowerWater() {
        sut.placeAnimal(animal, PLOT_ID, user);
        Mockito.verify(inventoryService, Mockito.times(1)).lowerWater(START_WATER, user);
    }

    @Test
    void placeAnimalCallsAddAnimalToPlot() {
        sut.placeAnimal(animal, PLOT_ID, user);
        Mockito.verify(plotDAO, Mockito.times(1)).addAnimalToPlot(animal, PLOT_ID);
    }

    @Test
    void placeAnimalCallsGetFarm() {
        sut.placeAnimal(animal, PLOT_ID, user);
        Mockito.verify(farmDAO, Mockito.times(1)).getFarm(user);
    }

    @Test
    void placeAnimalReturnsAllPlots() {
        Assertions.assertEquals(allPlots.getPlots().size(), sut.placeAnimal(animal, PLOT_ID, user).getPlots().size());
    }
}
