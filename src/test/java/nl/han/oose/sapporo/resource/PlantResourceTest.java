package nl.han.oose.sapporo.resource;

import nl.han.oose.sapporo.dto.AllPlantDTO;
import nl.han.oose.sapporo.service.IAccountService;
import nl.han.oose.sapporo.service.IPlantService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;

public class PlantResourceTest {
    private String token = "1234567890";
    private PlantResource sut = new PlantResource();
    private IAccountService accountService = Mockito.mock(IAccountService.class);
    private IPlantService plantService = Mockito.mock(IPlantService.class);
    private AllPlantDTO allpants = new AllPlantDTO(null);

    public PlantResourceTest() {
        sut.setAccountService(accountService);
        sut.setPlantService(plantService);
        Mockito.when(plantService.getAllPlants()).thenReturn(allpants);
    }

    @Test
    public void getAllPlantsCallsAuthenticateByToken() {
        sut.getAllPlants(token);
        Mockito.verify(accountService, Mockito.times(1)).authenticateByToken(token);
    }

    @Test
    public void getAllPlantsCallsgetAllPlants() {
        sut.getAllPlants(token);
        Mockito.verify(plantService, Mockito.times(1)).getAllPlants();
    }

    @Test
    public void getAllPlantsReturnsRightAllPlants() {
        Assertions.assertEquals(allpants, sut.getAllPlants(token).getEntity());
    }
}