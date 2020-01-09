package nl.han.oose.sapporo.resource;

import nl.han.oose.sapporo.service.IAccountService;
import nl.han.oose.sapporo.service.IBuildingService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class BuildingResourceTest {
    private BuildingResource sut = new BuildingResource();
    private IAccountService accountService = Mockito.mock(IAccountService.class);
    private IBuildingService buildingService = Mockito.mock(IBuildingService.class);
    private String token = "12345";

    BuildingResourceTest(){
        sut.setAccountService(accountService);
        sut.setIBuildingService(buildingService);
    }

    @Test
    void getAllWaterSourcesCallsVerifyToken(){
        sut.getAllWaterSources(token);
        Mockito.verify(accountService, Mockito.times(1)).verifyToken(token);
    }

    @Test
    void getAllWaterSourcesCallsGetAllWaterSources(){
        sut.getAllWaterSources(token);
        Mockito.verify(buildingService, Mockito.times(1)).getAllWaterSources();
    }
}
