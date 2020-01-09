package nl.han.oose.sapporo.service;

import nl.han.oose.sapporo.dto.*;
import nl.han.oose.sapporo.persistence.*;
import nl.han.oose.sapporo.service.exception.PlotIsAlreadyPurchasedException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

class PlotServiceTest {
    private PlotServiceImp sut = new PlotServiceImp();
    private IActionService actionService = Mockito.mock(IActionService.class);
    private IPlantService mockedPlantService = Mockito.mock(IPlantService.class);
    private IAnimalService mockedAnimalService = Mockito.mock(IAnimalService.class);
    private IPlotDAO mockedPlotDAO = Mockito.mock(IPlotDAO.class);
    private IPlantDAO mockedPlantDAO = Mockito.mock(PlantDAOImp.class);
    private IAnimalDAO mockedAnimalDAO = Mockito.mock(IAnimalDAO.class);
    private IFarmDAO mockedFarmDAO = Mockito.mock(IFarmDAO.class);
    private IInventoryService mockedInventoryService = Mockito.mock(IInventoryService.class);
    private final int PLOT_ID = 1;
    private final float PRICE = 5;
    private final int FARM_ID = 1;
    private final int WATER = 10;
    private final int AGE = 0;
    private final int ANIMAL_ID = 1;
    private final int PROFIT = 7;
    private final FarmDTO FARM = new FarmDTO(FARM_ID, 1);
    private final PlantDTO PLANT = new PlantDTO(1, "Cabbage", 5, 10, 20, PRICE,100);
    private final UserDTO USER = new UserDTO(1, "PatrickSt3r", "DC00C903852BB19EB250AEBA05E534A6D211629D77D055033806B783BAE09937", "Patrick@Ster.com");
    private final PlotDTO PLOT = new PlotDTO(1, 1, 1, 0, 0, 1, PRICE, true, 10, 30);
    private final PlotDTO PLOT_WITH_GROWN_PLANT = new PlotDTO(1, 1, 1, 1, 0, 1, 0, 100);
    private final AnimalDTO ANIMAL = new AnimalDTO(1, "Cabbage", 10, 300, 10, 20, PRICE);

    PlotServiceTest() {
        sut.setPlotDAO(mockedPlotDAO);
        sut.setInventoryService(mockedInventoryService);
        sut.setPlantService(mockedPlantService);
        sut.setPlantDAO(mockedPlantDAO);
        sut.setAnimalDAO(mockedAnimalDAO);
        sut.setFarmDAO(mockedFarmDAO);
        sut.setActionService(actionService);
        sut.setAnimalService(mockedAnimalService);

        Mockito.when(mockedPlotDAO.getPlot(PLOT_ID)).thenReturn(PLOT_WITH_GROWN_PLANT);
        Mockito.when(mockedPlotDAO.plotHasPlant(PLOT_ID)).thenReturn(true);
        Mockito.when(mockedInventoryService.checkIfPlayerHasEnoughSaldo(PRICE, USER)).thenReturn(true);
        Mockito.when(mockedFarmDAO.getFarm(USER)).thenReturn(FARM);
    }

    @Test
    void placePlantCallsCheckSaldo() {
        sut.placePlant(PLANT, PLOT_ID, USER);
        Mockito.verify(mockedInventoryService, Mockito.times(1)).checkIfPlayerHasEnoughSaldo(PRICE, USER);
    }

    @Test
    void placePlantCallsCheckIfPlotIsEmpty() {
        Mockito.when(mockedPlotDAO.checkIfPlotIsEmpty(PLOT_ID)).thenReturn(true);
        sut.placePlant(PLANT, PLOT_ID, USER);
        Mockito.verify(mockedPlotDAO, Mockito.times(1)).checkIfPlotIsEmpty(PLOT_ID);
    }

    @Test
    void placePlantCallsLowerSaldo() {
        Mockito.when(mockedPlotDAO.checkIfPlotIsEmpty(PLOT_ID)).thenReturn(true);
        sut.placePlant(PLANT, PLOT_ID, USER);
        Mockito.verify(mockedInventoryService, Mockito.times(1)).lowerSaldo(PRICE, USER);
    }

    @Test
    void placePlantCallsAddPlantToPlot() {
        Mockito.when(mockedPlotDAO.checkIfPlotIsEmpty(PLOT_ID)).thenReturn(true);
        sut.placePlant(PLANT, PLOT_ID, USER);
        Mockito.verify(mockedPlotDAO, Mockito.times(1)).addPlantToPlot(PLANT, PLOT_ID);
    }

    @Test
    void placePlantCallsGetPlot() {
        Mockito.when(mockedPlotDAO.checkIfPlotIsEmpty(PLOT_ID)).thenReturn(true);
        sut.placePlant(PLANT, PLOT_ID, USER);
        Mockito.verify(mockedPlotDAO, Mockito.times(1)).getPlot(PLOT_ID);
    }

    @Test
    void placePlantCallsActionService() {
        Mockito.when(mockedPlotDAO.checkIfPlotIsEmpty(PLOT_ID)).thenReturn(true);
        final int PLANT_SEED_ACTION_ID = 1;
        sut.placePlant(PLANT, PLOT_ID, USER);
        Mockito.verify(actionService, Mockito.times(1)).setAction(USER,PLANT_SEED_ACTION_ID,PLANT.getName());
    }

    @Test
    void placePlantReturnsPlot() {
        Mockito.when(mockedPlotDAO.checkIfPlotIsEmpty(PLOT_ID)).thenReturn(true);
        Mockito.when(mockedPlotDAO.getPlot(PLOT_ID)).thenReturn(PLOT);
        Assertions.assertEquals(PLOT, sut.placePlant(PLANT, PLOT_ID, USER));
    }

    @Test
    void harvestPlantCallsPlantFullGrown() {
        Mockito.when(mockedPlotDAO.getPlot(PLOT_ID)).thenReturn(PLOT);
        Mockito.when(mockedPlantService.plantFullGrown(PLOT)).thenReturn(true);
        sut.harvestPlant(PLOT_WITH_GROWN_PLANT, USER, PLOT_ID);
        Mockito.verify(mockedPlantService, Mockito.times(1)).plantFullGrown(PLOT);
    }

    @Test
    void harvestPlantCallsRemoveObjectsFromPlot() {
        Mockito.when(mockedPlotDAO.getPlot(PLOT_ID)).thenReturn(PLOT);
        Mockito.when(mockedPlantService.plantFullGrown(PLOT)).thenReturn(true);
        sut.harvestPlant(PLOT_WITH_GROWN_PLANT, USER, PLOT_ID);
        Mockito.verify(mockedPlotDAO, Mockito.times(1)).removeObjectsFromPlot(PLOT_ID);
    }

    @Test
    void harvestPlantCallsIncreaseSaldo() {
        Mockito.when(mockedPlantService.plantFullGrown(PLOT_WITH_GROWN_PLANT)).thenReturn(true);
        Mockito.when(mockedPlantDAO.getProfit(PLANT.getId())).thenReturn(20);
        sut.harvestPlant(PLOT_WITH_GROWN_PLANT, USER, PLOT_ID);
        Mockito.verify(mockedInventoryService, Mockito.times(1)).increaseSaldo(PLANT.getProfit(), USER);
    }

    @Test
    void harvestPlantCallsGetPlot() {
        Mockito.when(mockedPlantService.plantFullGrown(PLOT_WITH_GROWN_PLANT)).thenReturn(true);
        sut.harvestPlant(PLOT_WITH_GROWN_PLANT, USER, PLOT_ID);
        Mockito.verify(mockedPlotDAO, Mockito.times(2)).getPlot(PLOT_ID);
    }

    @Test
    void harvestPlantCallsActionService() {
        Mockito.when(mockedPlotDAO.getPlot(PLOT_ID)).thenReturn(PLOT);
        Mockito.when(mockedPlantService.plantFullGrown(PLOT)).thenReturn(true);
        Mockito.when(mockedPlotDAO.plotHasPlant(PLOT_ID)).thenReturn(true);
        final int HARVEST_PLANT_ACTION_ID = 2;
        final String NAME = "orange";
        Mockito.when(mockedPlantDAO.getName(1)).thenReturn(NAME);
        sut.harvestPlant(PLOT_WITH_GROWN_PLANT, USER, PLOT_ID);
        Mockito.verify(actionService, Mockito.times(1)).setAction(USER,HARVEST_PLANT_ACTION_ID,NAME);
    }

    @Test
    void harvestPlantReturnsRightPlot() {
        Mockito.when(mockedPlantService.plantFullGrown(PLOT_WITH_GROWN_PLANT)).thenReturn(true);
        Assertions.assertEquals(PLOT_WITH_GROWN_PLANT, sut.harvestPlant(PLOT_WITH_GROWN_PLANT, USER, PLOT_ID));
    }

    @Test
    void harvestPlantThrowsExceptionWhenNotGrown() {
        final PlotDTO PLOT_WITHOUT_GROWN_PLANT = new PlotDTO(2, 1, 1, 1, 0, 1, 0, 0);
        Assertions.assertDoesNotThrow(() -> {
            sut.harvestPlant(PLOT_WITHOUT_GROWN_PLANT, USER, PLOT_ID);
        });
    }

    @Test
    void getFarmPlotsCallsGetFarmPlots() {
        sut.getFarmPlots(FARM_ID);
        Mockito.verify(mockedPlotDAO, Mockito.times(1)).getFarmPlots(PLOT_ID);
    }

    @Test
    void getFarmPlotsReturnsPlots() {
        final ArrayList<PlotDTO> PLOTS = new ArrayList<>() {{ add(PLOT); }};
        Mockito.when(mockedPlotDAO.getFarmPlots(FARM_ID)).thenReturn(PLOTS);
        Assertions.assertEquals(PLOTS, sut.getFarmPlots(FARM_ID));
    }

    @Test
    void updateAgeCallsUpdateAge() {
        sut.updateAge(1,1000);
        Mockito.verify(mockedPlotDAO, Mockito.times(1)).updateAge(1,1000);
    }

    @Test
    void purchasePlotCallsGetPlot() {
        sut.purchasePlot(PLOT_ID, USER);
        Mockito.verify(mockedPlotDAO, Mockito.times(1)).getPlot(PLOT_ID);
    }

    @Test
    void purchasePlotCallsPlotIsPurchased() {
        sut.purchasePlot(PLOT_ID, USER);
        Mockito.verify(mockedPlotDAO, Mockito.times(1)).plotIsPurchased(PLOT_ID);
    }

    @Test
    void purchasePlotCallsActionService() {
        Mockito.when(mockedPlotDAO.plotIsPurchased(PLOT_ID)).thenReturn(false);
        Mockito.when(mockedFarmDAO.getFarm(USER)).thenReturn(FARM);
        Mockito.when(mockedPlotDAO.getPlot(PLOT_ID)).thenReturn(PLOT);
        final int PURCHASE_PLOT_ACTION_ID = 4;
        sut.purchasePlot(PLOT_ID, USER);
        Mockito.verify(actionService, Mockito.times(1)).setAction(USER,PURCHASE_PLOT_ACTION_ID,null);
    }

    @Test
    void editWaterCallsFirstGetFunctions() {
        Mockito.when(mockedPlotDAO.checkIfPlotHasWater(1)).thenReturn(true);

        sut.editWater(USER, 1, 20, false);

        Mockito.verify(mockedPlotDAO,Mockito.times(2)).getPlot(1);
        Mockito.verify(mockedPlantDAO,Mockito.times(1)).getName(1);
    }

    @Test
    void editWaterCallsWaterFunctions() {
        Mockito.when(mockedPlotDAO.getPlot(1)).thenReturn(PLOT);
        Mockito.when(mockedPlotDAO.checkIfPlotHasWater(1)).thenReturn(true);
        Mockito.when(mockedInventoryService.checkIfPlayerHasEnoughWater(30,USER)).thenReturn(true);

        sut.editWater(USER, 1, 30, true);

        Mockito.verify(mockedInventoryService,Mockito.times(1)).checkIfPlayerHasEnoughWater(30,USER);
        Mockito.verify(mockedInventoryService,Mockito.times(1)).lowerWater(-30,USER);
        Mockito.verify(mockedPlotDAO,Mockito.times(1)).editWaterAvailable(-30,1);
    }

    @Test
    void purchasePlotThrowsExceptionWhenPlotIsAlreadyPurchased() {
        Mockito.when(mockedPlotDAO.plotIsPurchased(PLOT_ID)).thenReturn(true);
        Assertions.assertThrows(PlotIsAlreadyPurchasedException.class, () -> { sut.purchasePlot(PLOT_ID, USER); });
    }

    @Test
    void purchasePlotThrowsPlotIsAlreadyPurchasedExceptionWhenPlotIsPurchased() {
        Mockito.when(mockedPlotDAO.plotIsPurchased(PLOT_ID)).thenReturn(true);
        Assertions.assertThrows(PlotIsAlreadyPurchasedException.class, () -> sut.purchasePlot(PLOT_ID, USER));
    }

    @Test
    void purchasePlotReturnsCallsLowerSaldoInInventoryService() {
        Mockito.when(mockedPlotDAO.getPlot(PLOT_ID)).thenReturn(PLOT);
        Mockito.when(mockedPlotDAO.plotIsPurchased(PLOT_ID)).thenReturn(false);
        Mockito.when(mockedFarmDAO.getFarm(USER)).thenReturn(FARM);
        sut.purchasePlot(PLOT_ID, USER);
        Mockito.verify(mockedInventoryService, Mockito.times(1)).lowerSaldo(PRICE, USER);
    }

    @Test
    void placeAnimalCallsCheckIfPlotIsEmpty() {
        sut.placeAnimal(ANIMAL, PLOT_ID, USER);
        Mockito.verify(mockedPlotDAO, Mockito.times(1)).checkIfPlotIsEmpty(PLOT_ID);
    }

    @Test
    void placeAnimalCallsLowerSaldo() {
        final FarmDTO FARM = new FarmDTO(FARM_ID, USER.getId());
        Mockito.when(mockedPlotDAO.checkIfPlotIsEmpty(PLOT_ID)).thenReturn(true);
        Mockito.when(mockedFarmDAO.getFarm(USER)).thenReturn(FARM);
        sut.placeAnimal(ANIMAL, PLOT_ID, USER);
        Mockito.verify(mockedInventoryService, Mockito.times(1)).lowerSaldo(PRICE, USER);
    }

    @Test
    void placeAnimalCallsLowerWater() {
        final FarmDTO FARM = new FarmDTO(FARM_ID, USER.getId());
        Mockito.when(mockedPlotDAO.checkIfPlotIsEmpty(PLOT_ID)).thenReturn(true);
        Mockito.when(mockedFarmDAO.getFarm(USER)).thenReturn(FARM);
        final int START_WATER = 25;
        sut.placeAnimal(ANIMAL, PLOT_ID, USER);
        Mockito.verify(mockedInventoryService, Mockito.times(1)).lowerWater(START_WATER, USER);
    }

    @Test
    void placeAnimalCallsAddAnimalToPlot() {
        final FarmDTO FARM = new FarmDTO(FARM_ID, USER.getId());
        Mockito.when(mockedPlotDAO.checkIfPlotIsEmpty(PLOT_ID)).thenReturn(true);
        Mockito.when(mockedFarmDAO.getFarm(USER)).thenReturn(FARM);
        sut.placeAnimal(ANIMAL, PLOT_ID, USER);
        Mockito.verify(mockedPlotDAO, Mockito.times(1)).addAnimalToPlot(ANIMAL, PLOT_ID);
    }

    @Test
    void placeAnimalCallsGetFarm() {
        final FarmDTO FARM = new FarmDTO(FARM_ID, USER.getId());
        Mockito.when(mockedPlotDAO.checkIfPlotIsEmpty(PLOT_ID)).thenReturn(true);
        Mockito.when(mockedFarmDAO.getFarm(USER)).thenReturn(FARM);
        sut.placeAnimal(ANIMAL, PLOT_ID, USER);
        Mockito.verify(mockedFarmDAO, Mockito.times(1)).getFarm(USER);
    }

    @Test
    void placeAnimalReturnsAllPlots() {
        final FarmDTO FARM = new FarmDTO(FARM_ID, USER.getId());
        final ArrayList<PlotDTO> PLOTS = new ArrayList<>() {{ add(PLOT); }};
        final AllPlotDTO ALL_PLOTS = new AllPlotDTO(PLOTS);
        Mockito.when(mockedInventoryService.checkIfPlayerHasEnoughSaldo(PRICE, USER)).thenReturn(true);
        Mockito.when(mockedPlotDAO.checkIfPlotIsEmpty(PLOT_ID)).thenReturn(true);
        Mockito.when(mockedFarmDAO.getFarm(USER)).thenReturn(FARM);
        Mockito.when(mockedPlotDAO.getFarmPlots(FARM_ID)).thenReturn(PLOTS);

        Assertions.assertEquals(ALL_PLOTS.getPlots().size(), sut.placeAnimal(ANIMAL, PLOT_ID, USER).getPlots().size());
    }

    @Test
    void sellProductCallsAnimalProductIsCollectable() {
        sut.sellProduct(PLOT_WITH_GROWN_PLANT, USER, PLOT_ID);
        Mockito.verify(mockedAnimalService, Mockito.times(1)).animalProductIsCollectable(PLOT_WITH_GROWN_PLANT);
    }

    @Test
    void sellProductCallsPlotHasAnimal() {
        Mockito.when(mockedAnimalService.animalProductIsCollectable(PLOT_WITH_GROWN_PLANT)).thenReturn(true);
        sut.sellProduct(PLOT_WITH_GROWN_PLANT, USER, PLOT_ID);
        Mockito.verify(mockedPlotDAO, Mockito.times(1)).plotHasAnimal(PLOT_ID);
    }

    @Test
    void sellProductCallsUpdateAge() {
        Mockito.when(mockedAnimalService.animalProductIsCollectable(PLOT_WITH_GROWN_PLANT)).thenReturn(true);
        Mockito.when(mockedPlotDAO.plotHasAnimal(PLOT_ID)).thenReturn(true);
        sut.sellProduct(PLOT_WITH_GROWN_PLANT, USER, PLOT_ID);
        Mockito.verify(mockedPlotDAO, Mockito.times(1)).updateAge(PLOT_ID, AGE);
    }

    @Test
    void sellProductCallsGetProductProfit() {
        Mockito.when(mockedAnimalService.animalProductIsCollectable(PLOT_WITH_GROWN_PLANT)).thenReturn(true);
        Mockito.when(mockedPlotDAO.plotHasAnimal(PLOT_ID)).thenReturn(true);
        Mockito.when(mockedAnimalDAO.getProductProfit(ANIMAL_ID)).thenReturn(PROFIT);
        sut.sellProduct(PLOT_WITH_GROWN_PLANT, USER, PLOT_ID);
        Mockito.verify(mockedAnimalDAO, Mockito.times(1)).getProductProfit(ANIMAL_ID);
    }

    @Test
    void sellProductCallsIncreaseSaldo() {
        Mockito.when(mockedAnimalService.animalProductIsCollectable(PLOT_WITH_GROWN_PLANT)).thenReturn(true);
        Mockito.when(mockedPlotDAO.plotHasAnimal(PLOT_ID)).thenReturn(true);
        Mockito.when(mockedAnimalDAO.getProductProfit(ANIMAL_ID)).thenReturn(PROFIT);
        sut.sellProduct(PLOT_WITH_GROWN_PLANT, USER, PLOT_ID);
        Mockito.verify(mockedInventoryService, Mockito.times(1)).increaseSaldo(PROFIT, USER);
    }

    @Test
    void sellProductCallsActionService() {
        Mockito.when(mockedAnimalService.animalProductIsCollectable(PLOT_WITH_GROWN_PLANT)).thenReturn(true);
        Mockito.when(mockedPlotDAO.plotHasAnimal(PLOT_ID)).thenReturn(true);
        Mockito.when(mockedAnimalDAO.getProductProfit(ANIMAL_ID)).thenReturn(PROFIT);
        final int SELL_PRODUCT_ACTION_ID = 8;
        final String NAME = "Cow";
        Mockito.when(mockedAnimalDAO.getAnimal(ANIMAL_ID)).thenReturn(NAME);
        sut.sellProduct(PLOT_WITH_GROWN_PLANT, USER, PLOT_ID);
        Mockito.verify(actionService, Mockito.times(1)).setAction(USER,SELL_PRODUCT_ACTION_ID,NAME);
    }

    @Test
    void sellProductReturnsAllPlots() {
        final FarmDTO FARM = new FarmDTO(FARM_ID, USER.getId());
        final ArrayList<PlotDTO> PLOTS = new ArrayList<>() {{ add(PLOT); }};
        final AllPlotDTO ALL_PLOTS = new AllPlotDTO(PLOTS);
        Mockito.when(mockedAnimalService.animalProductIsCollectable(PLOT_WITH_GROWN_PLANT)).thenReturn(true);
        Mockito.when(mockedPlotDAO.plotHasAnimal(PLOT_ID)).thenReturn(true);
        Mockito.when(mockedPlotDAO.getFarmPlots(FARM_ID)).thenReturn(PLOTS);

        Assertions.assertEquals(ALL_PLOTS.getPlots().size(), sut.sellProduct(PLOT_WITH_GROWN_PLANT, USER, PLOT_ID).getPlots().size());
    }

    @Test
    void replaceAnimalsOnAllPlotssCallsReplaceAnimalsOnAllPlots() {
        final int DELETEID =1;
        final int REPLACEID =2;
        sut.replaceAnimalsOnAllPlots(DELETEID,REPLACEID);
        Mockito.verify(mockedPlotDAO, Mockito.times(1)).replaceAnimalsOnAllPlots(DELETEID,REPLACEID);
    }

    @Test
    void replacePlantsOnAllPlotsCallsReplacePlantsOnAllPlots(){
        final int DELETEID =1;
        final int REPLACEID =2;
        sut.replacePlantsOnAllPlots(DELETEID,REPLACEID);
        Mockito.verify(mockedPlotDAO, Mockito.times(1)).replacePlantsOnAllPlots(DELETEID,REPLACEID);
    }

    @Test
    void changeStatusCallsChangeStatus(){
        final int PLOTID =1;
        final String STATUS = "Status";
        sut.changeStatus(PLOTID,STATUS);
        Mockito.verify(mockedPlotDAO, Mockito.times(1)).changeStatus(PLOTID,STATUS);
    }

    @Test
    void changeStatusCallsGetPlot(){
        final int PLOTID =1;
        final String STATUS = "Status";
        sut.changeStatus(PLOTID,STATUS);
        Mockito.verify(mockedPlotDAO, Mockito.times(1)).getPlot(PLOTID);

    }

    @Test
    public void clearPlotCallsRemoveObjects() {
        sut.clearPlot(USER, PLOT_ID);
        Mockito.verify(mockedPlotDAO,Mockito.times(1)).removeObjectsFromPlot(PLOT_ID);
    }
}
