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
    private IPlantService mockedPlantService = Mockito.mock(IPlantService.class);
    private IPlotDAO mockedPlotDAO = Mockito.mock(IPlotDAO.class);
    private IPlantDAO mockedPlantDAO = Mockito.mock(PlantDAOImp.class);
    private IFarmDAO mockedFarmDAO = Mockito.mock(IFarmDAO.class);
    private IInventoryService mockedInventoryService = Mockito.mock(IInventoryService.class);

    private final int PLOT_ID = 1;
    private final float PRICE = 5;
    private final int FARM_ID = 1;
    private final int WATER = 10;
    private final PlantDTO PLANT = new PlantDTO(1, "Cabbage", 5, 10, 20, PRICE);
    private final UserDTO USER = new UserDTO(1, "PatrickSt3r", "DC00C903852BB19EB250AEBA05E534A6D211629D77D055033806B783BAE09937", "Patrick@Ster.com");
    private final PlotDTO PLOT = new PlotDTO(1, 1, 1, 1, 0, 0, PRICE);
    private final PlotDTO PLOT_WITH_GROWN_PLANT = new PlotDTO(1, 1, 1, 1, 0, 1, 0, 100);

    PlotServiceTest() {
        sut.setPlotDAO(mockedPlotDAO);
        sut.setInventoryService(mockedInventoryService);
        sut.setPlantService(mockedPlantService);
        sut.setPlantDAO(mockedPlantDAO);
        sut.setFarmDAO(mockedFarmDAO);

        Mockito.when(mockedPlotDAO.getPlot(PLOT_ID)).thenReturn(PLOT_WITH_GROWN_PLANT);
        Mockito.when(mockedPlotDAO.plotHasPlant(PLOT_ID)).thenReturn(true);
    }

    @Test
    void placePlantCallsCheckSaldo() {
        Mockito.when(mockedInventoryService.checkIfPlayerHasEnoughSaldo(PRICE, USER)).thenReturn(true);
        sut.placePlant(PLANT, PLOT_ID, USER);
        Mockito.verify(mockedInventoryService, Mockito.times(1)).checkIfPlayerHasEnoughSaldo(PRICE, USER);
    }

    @Test
    void placePlantCallsCheckIfPlotIsEmpty() {
        Mockito.when(mockedInventoryService.checkIfPlayerHasEnoughSaldo(PRICE, USER)).thenReturn(true);
        Mockito.when(mockedPlotDAO.checkIfPlotIsEmpty(PLOT_ID)).thenReturn(true);
        sut.placePlant(PLANT, PLOT_ID, USER);
        Mockito.verify(mockedPlotDAO, Mockito.times(1)).checkIfPlotIsEmpty(PLOT_ID);
    }

    @Test
    void placePlantCallsLowerSaldo() {
        Mockito.when(mockedInventoryService.checkIfPlayerHasEnoughSaldo(PRICE, USER)).thenReturn(true);
        Mockito.when(mockedPlotDAO.checkIfPlotIsEmpty(PLOT_ID)).thenReturn(true);
        sut.placePlant(PLANT, PLOT_ID, USER);
        Mockito.verify(mockedInventoryService, Mockito.times(1)).lowerSaldo(PRICE, USER);
    }

    @Test
    void placePlantCallsAddPlantToPlot() {
        Mockito.when(mockedInventoryService.checkIfPlayerHasEnoughSaldo(PRICE, USER)).thenReturn(true);
        Mockito.when(mockedPlotDAO.checkIfPlotIsEmpty(PLOT_ID)).thenReturn(true);
        sut.placePlant(PLANT, PLOT_ID, USER);
        Mockito.verify(mockedPlotDAO, Mockito.times(1)).addPlantToPlot(PLANT, PLOT_ID);
    }

    @Test
    void placePlantCallsgetPlot() {
        Mockito.when(mockedInventoryService.checkIfPlayerHasEnoughSaldo(PRICE, USER)).thenReturn(true);
        Mockito.when(mockedPlotDAO.checkIfPlotIsEmpty(PLOT_ID)).thenReturn(true);
        sut.placePlant(PLANT, PLOT_ID, USER);
        Mockito.verify(mockedPlotDAO, Mockito.times(1)).getPlot(PLOT_ID);
    }

    @Test
    void placePlantReturnsPlot() {
        Mockito.when(mockedInventoryService.checkIfPlayerHasEnoughSaldo(PRICE, USER)).thenReturn(true);
        Mockito.when(mockedPlotDAO.checkIfPlotIsEmpty(PLOT_ID)).thenReturn(true);
        Assertions.assertEquals(PLOT_WITH_GROWN_PLANT, sut.placePlant(PLANT, PLOT_ID, USER));
    }

    @Test
    void harvestPlantCallsPlantFullGrown() {
        sut.harvestPlant(PLOT_WITH_GROWN_PLANT, USER, PLOT_ID);
        Mockito.verify(mockedPlantService, Mockito.times(1)).plantFullGrown(PLOT_WITH_GROWN_PLANT);
    }

    @Test
    void harvestPlantCallsRemoveObjectsFromPlot() {
        Mockito.when(mockedPlantService.plantFullGrown(PLOT_WITH_GROWN_PLANT)).thenReturn(true);
        sut.harvestPlant(PLOT_WITH_GROWN_PLANT, USER, PLOT_ID);
        Mockito.verify(mockedPlotDAO, Mockito.times(1)).removeObjectsFromPlot(PLOT_ID);
    }

    @Test
    void harvestPlantCallsIncreaseSaldo() {
        Mockito.when(mockedPlantService.plantFullGrown(PLOT_WITH_GROWN_PLANT)).thenReturn(true);
        Mockito.when(mockedPlantDAO.getProfit(PLANT.getID())).thenReturn(20);
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
    void waterPlantCallsCheckWater(){
        sut.editWater(USER, PLOT_ID, WATER);
        Mockito.verify(mockedInventoryService, Mockito.times(1)).checkIfPlayerHasEnoughWater(WATER, USER);
    }

    @Test
    void waterPlantCallsPlotHasPlant(){
        Mockito.when(mockedInventoryService.checkIfPlayerHasEnoughWater(10, USER)).thenReturn(true);
        sut.editWater(USER, PLOT_ID, WATER);
        Mockito.verify(mockedPlotDAO, Mockito.times(1)).plotHasPlant(PLOT_ID);
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
        final FarmDTO FARM = new FarmDTO(1, 1);
        Mockito.when(mockedPlotDAO.getPlot(PLOT_ID)).thenReturn(PLOT);
        Mockito.when(mockedInventoryService.checkIfPlayerHasEnoughSaldo(PRICE, USER)).thenReturn(true);
        Mockito.when(mockedPlotDAO.plotIsPurchased(PLOT_ID)).thenReturn(false);
        Mockito.when(mockedFarmDAO.getFarm(USER)).thenReturn(FARM);
        sut.purchasePlot(PLOT_ID, USER);
        Mockito.verify(mockedInventoryService, Mockito.times(1)).lowerSaldo(PRICE, USER);
    }

    @Test
    void updatePlantsOnAllPlotsCallsUpdatePlantsOnAllPlotsInPlotDAO() {
        sut.updatePlantsOnAllPlots(1, 2);
        Mockito.verify(mockedPlotDAO, Mockito.times(1)).updatePlantsOnAllPlots(1, 2);
    }
}
