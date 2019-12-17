package nl.han.oose.sapporo.resource;

import nl.han.oose.sapporo.dto.AllPlantDTO;
import nl.han.oose.sapporo.dto.UserDTO;
import nl.han.oose.sapporo.service.IAccountService;
import nl.han.oose.sapporo.service.IAdminService;
import nl.han.oose.sapporo.service.IPlantService;
import nl.han.oose.sapporo.service.IPlotService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;

public class PlantResourceTest {
    private final String TOKEN = "1234567890";
    private PlantResource sut = new PlantResource();
    private IAccountService mockedAccountService = Mockito.mock(IAccountService.class);
    private IPlantService mockedPlantService = Mockito.mock(IPlantService.class);
    private AllPlantDTO ALL_PLANTS = new AllPlantDTO(null);
    private UserDTO USER = new UserDTO();

    public PlantResourceTest() {
        sut.setAccountService(mockedAccountService);
        sut.setPlantService(mockedPlantService);
        Mockito.when(mockedAccountService.verifyToken(TOKEN)).thenReturn(USER);
        Mockito.when(mockedPlantService.getAllPlants()).thenReturn(ALL_PLANTS);
    }

    @Test
    public void getAllPlantsCallsAuthenticateByToken() {
        sut.getAllPlants(TOKEN);
        Mockito.verify(mockedAccountService, Mockito.times(1)).verifyToken(TOKEN);
    }

    @Test
    public void getAllPlantsCallsGetAllPlants() {
        sut.getAllPlants(TOKEN);
        Mockito.verify(mockedPlantService, Mockito.times(1)).getAllPlants();
    }

    @Test
    public void getAllPlantsReturnsRightAllPlants() {
        Assertions.assertEquals(ALL_PLANTS, sut.getAllPlants(TOKEN).getEntity());
    }

    @Test
    public void deletePlantCallsAuthenticateByToken() {
        sut.deletePlant(TOKEN, 1, 2);
        Mockito.verify(mockedAccountService, Mockito.times(1)).verifyToken(TOKEN);
    }

    @Test
    public void deletePlantCallsDeletePlantInPlantService() {
        sut.deletePlant(TOKEN, 1, 2);
        Mockito.verify(mockedPlantService, Mockito.times(1)).deletePlant(USER, 1, 2);
    }

    @Test
    public void deletePlantReturnsRightAllPlants() {
        Assertions.assertEquals(ALL_PLANTS, sut.getAllPlants(TOKEN).getEntity());
    }
}
