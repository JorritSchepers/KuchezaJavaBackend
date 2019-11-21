package nl.han.oose.sapporo.service;

import nl.han.oose.sapporo.dto.PlantDTO;
import nl.han.oose.sapporo.dto.PlotDTO;
import nl.han.oose.sapporo.dto.UserDTO;
import nl.han.oose.sapporo.persistence.IPlotDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class PlotServiceTest {
    private PlotServiceImp sut = new PlotServiceImp();
    private IPlotDAO plotDAO = Mockito.mock(IPlotDAO.class);
    private IInventoryService inventoryService = Mockito.mock(IInventoryService.class);
    private final int PLOTID = 1;
    private final float PRICE = 10;
    private PlantDTO plant = new PlantDTO(1,"test",1,1,1,PRICE,1);
    private UserDTO user = new UserDTO();
    private PlotDTO plot = new PlotDTO(1,1,1,1);

    PlotServiceTest(){
        sut.setPlotDAO(plotDAO);
        sut.setInventoryService(inventoryService);
        Mockito.when(inventoryService.checkSaldo(PRICE,user)).thenReturn(true);
        Mockito.when(plotDAO.getPlot(PLOTID)).thenReturn(plot);
    }

    @Test
    void placePlantCallsCheckSaldo (){
        sut.placePlant(plant,PLOTID,user);
        Mockito.verify(inventoryService, Mockito.times(1)).checkSaldo(PRICE,user);
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
}
