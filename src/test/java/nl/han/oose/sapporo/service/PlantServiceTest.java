package nl.han.oose.sapporo.service;

import nl.han.oose.sapporo.dto.AllPlantDTO;
import nl.han.oose.sapporo.dto.PlantDTO;
import nl.han.oose.sapporo.dto.PlotDTO;
import nl.han.oose.sapporo.persistence.IPlantDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class PlantServiceTest {
    private PlantServiceImp sut = new PlantServiceImp();
    private IPlantDAO plantDAO;
    private AllPlantDTO allPlantDTO = new AllPlantDTO(null);
    private PlantDTO grownPlant = new PlantDTO();
    private PlantDTO notGrownPlant = new PlantDTO();
    private PlotDTO plotWithGrownPlant = new PlotDTO(1, 1, 1, 1, 0, 0, 0, 100);
    private PlotDTO plotWithoutGrownPlant = new PlotDTO(2, 1, 1, 1, 0, 0, 0, 0);

    PlantServiceTest() {
        plantDAO = Mockito.mock(IPlantDAO.class);
        Mockito.when(plantDAO.getAllPlants()).thenReturn(allPlantDTO);
        Mockito.when(plantDAO.checkIfPlantFullGrown(plotWithGrownPlant)).thenReturn(true);
        Mockito.when(plantDAO.checkIfPlantFullGrown(plotWithoutGrownPlant)).thenReturn(false);
        sut.setPlantDAO(plantDAO);
    }

    @Test
    void getAllPlantsCallsGetAllPlants() {
        sut.getAllPlants();
        Mockito.verify(plantDAO, Mockito.times(1)).getAllPlants();
    }

    @Test
    void getAllPlantsReturnsRightGetAllPlants() {
        Assertions.assertEquals(allPlantDTO, sut.getAllPlants());
    }

    @Test
    void plantFullGrownCallscheckIfPlantFullGrown() {
        sut.plantFullGrown(plotWithGrownPlant);
        Mockito.verify(plantDAO, Mockito.times(1)).checkIfPlantFullGrown(plotWithGrownPlant);
    }
}