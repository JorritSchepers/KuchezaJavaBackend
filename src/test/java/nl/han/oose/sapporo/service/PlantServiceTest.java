package nl.han.oose.sapporo.service;

import nl.han.oose.sapporo.dto.AllPlantDTO;
import nl.han.oose.sapporo.dto.PlotDTO;
import nl.han.oose.sapporo.persistence.IPlantDAO;
import nl.han.oose.sapporo.persistence.exception.PlantNotGrownException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class PlantServiceTest {
    private PlantServiceImp sut = new PlantServiceImp();
    private IPlantDAO mockedPlantDAO = Mockito.mock(IPlantDAO.class);
    private AllPlantDTO ALLPLANTDTO = new AllPlantDTO(null);
    private PlotDTO PLOT_WITH_GROWN_PLANT = new PlotDTO(1, 1, 1, 1, 0, 0, 0, 100);
    private PlotDTO PLOT_WITHOUT_GROWN_PLANT = new PlotDTO(2, 1, 1, 1, 0, 0, 0, 0);

    PlantServiceTest() {
        Mockito.when(mockedPlantDAO.getAllPlants()).thenReturn(ALLPLANTDTO);
        Mockito.when(mockedPlantDAO.checkIfPlantFullGrown(PLOT_WITH_GROWN_PLANT)).thenReturn(true);
        Mockito.when(mockedPlantDAO.checkIfPlantFullGrown(PLOT_WITHOUT_GROWN_PLANT)).thenReturn(false);
        sut.setPlantDAO(mockedPlantDAO);
    }

    @Test
    void getAllPlantsCallsGetAllPlants() {
        sut.getAllPlants();
        Mockito.verify(mockedPlantDAO, Mockito.times(1)).getAllPlants();
    }

    @Test
    void getAllPlantsReturnsRightGetAllPlants() {
        Assertions.assertEquals(ALLPLANTDTO, sut.getAllPlants());
    }

    @Test
    void plantFullGrownCallsCheckIfPlantFullGrown() {
        sut.plantFullGrown(PLOT_WITH_GROWN_PLANT);
        Mockito.verify(mockedPlantDAO, Mockito.times(1)).checkIfPlantFullGrown(PLOT_WITH_GROWN_PLANT);
    }

    @Test
    void plantFullGrownThrowsPlantNotGrownExceptionWhenCalledWithPlotWithoutFullGrownPlant() {
        Assertions.assertThrows(PlantNotGrownException.class, () -> sut.plantFullGrown(PLOT_WITHOUT_GROWN_PLANT));
    }

    @Test
    void deletePlantCallsDeletePlantInPlantDAO() {
        sut.deletePlant(1);
        Mockito.verify(mockedPlantDAO, Mockito.times(1)).deletePlant(1);
    }
}
