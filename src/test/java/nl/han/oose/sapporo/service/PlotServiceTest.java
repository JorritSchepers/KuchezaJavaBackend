package nl.han.oose.sapporo.service;

import nl.han.oose.sapporo.dto.PlantDTO;
import nl.han.oose.sapporo.dto.PlotDTO;
import nl.han.oose.sapporo.dto.UserDTO;
import nl.han.oose.sapporo.persistence.IPlotDAO;
import nl.han.oose.sapporo.persistence.exception.PlantNotGrownException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class PlotServiceTest {
    private PlotServiceImp sut = new PlotServiceImp();
    private IPlantService plantService = Mockito.mock(IPlantService.class);
    private IPlotDAO plotDAO = Mockito.mock(IPlotDAO.class);
    private IInventoryService inventoryService = Mockito.mock(IInventoryService.class);
    private final int PLOTID = 1;
    private final float PRICE = 5;
    private PlantDTO plant = new PlantDTO(1,"Cabbage",5,10,20,PRICE,16);
    private PlantDTO notGrownPlant = new PlantDTO(1,"Cabbage",5,100,20,PRICE,5);
    private UserDTO user = new UserDTO(1,"PatrickSt3r","DC00C903852BB19EB250AEBA05E534A6D211629D77D055033806B783BAE09937","Patrick@Ster.com");
    private PlotDTO plot = new PlotDTO(1,1,1,1,0,0,0);


    PlotServiceTest(){
        sut.setPlotDAO(plotDAO);
        sut.setInventoryService(inventoryService);
        sut.setPlantService(plantService);
        Mockito.when(inventoryService.checkSaldo(PRICE,user)).thenReturn(true);
        Mockito.when(plotDAO.getPlot(PLOTID)).thenReturn(plot);
        Mockito.when(plotDAO.checkIfPlotIsEmpty(PLOTID)).thenReturn(true);
        Mockito.when(plantService.plantFullGrown(plant)).thenReturn(true);
        Mockito.when(plantService.plantFullGrown(notGrownPlant)).thenReturn(false);
    }

    @Test
    void placePlantCallsCheckSaldo (){
        sut.placePlant(plant,PLOTID,user);
        Mockito.verify(inventoryService, Mockito.times(1)).checkSaldo(PRICE,user);
    }

    @Test
    void placePlantCallsCheckIfPlotempy (){
        sut.placePlant(plant,PLOTID,user);
        Mockito.verify(plotDAO, Mockito.times(1)).checkIfPlotIsEmpty(PLOTID);
    }

    @Test
    void placePlantCallsLowerSaldo (){
        sut.placePlant(plant,PLOTID,user);
        Mockito.verify(inventoryService, Mockito.times(1)).lowerSaldo(PRICE,user);
    }

    @Test
    void placePlantCallsAddPlantToPlot (){
        sut.placePlant(plant,PLOTID,user);
        Mockito.verify(plotDAO, Mockito.times(1)).addPlantToPlot(plant,PLOTID);
    }

    @Test
    void placePlantCallsgetPlot (){
        sut.placePlant(plant,PLOTID,user);
        Mockito.verify(plotDAO, Mockito.times(1)).getPlot(PLOTID);
    }

    @Test
    void placePlantReturnsPlot (){
        Assertions.assertEquals(plot,sut.placePlant(plant,PLOTID,user));
    }

    @Test
    void harvestPlantCallsplantFullGrown (){
        sut.harvesPlant(plant,user,PLOTID);
        Mockito.verify(plantService, Mockito.times(1)).plantFullGrown(plant);
    }

    @Test
    void harvestPlantCallsremoveObjectsFromPlot(){
        sut.harvesPlant(plant,user,PLOTID);
        Mockito.verify(plotDAO, Mockito.times(1)).removeObjectsFromPlot(PLOTID);
    }

    @Test
    void harvestPlantCallsincreaseSaldo(){
        sut.harvesPlant(plant,user,PLOTID);
        Mockito.verify(inventoryService, Mockito.times(1)).increaseSaldo(plant.getProfit(),user);
    }

    @Test
    void harvestPlantCallsgetPlot(){
        sut.harvesPlant(plant,user,PLOTID);
        Mockito.verify(plotDAO, Mockito.times(1)).getPlot(PLOTID);
    }

    @Test
    void harvestPlantReturnsRightPlot(){
        Assertions.assertEquals(plot,sut.harvesPlant(plant,user,PLOTID));
    }

    @Test
    void harvestPlantThrowsNoExceptionWhenNotGrown(){
        Assertions.assertThrows(PlantNotGrownException.class, () -> {
            sut.harvesPlant(notGrownPlant,user,PLOTID);
        });
    }

    @Test
    void harvestPlantThrowsExceptionWhenNotGrown(){
        Assertions.assertDoesNotThrow(() -> {
            sut.harvesPlant(plant,user,PLOTID);
        });
    }
}