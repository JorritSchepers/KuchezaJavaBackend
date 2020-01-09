package nl.han.oose.sapporo.service;

import nl.han.oose.sapporo.persistence.IBuildingDAO;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class BuildingServiceTest {
    private BuildingServiceImp sut = new BuildingServiceImp();
    private IBuildingDAO buildingDAO = Mockito.mock(IBuildingDAO.class);

    BuildingServiceTest(){
        sut.setBuildingDAO(buildingDAO);
    }

    @Test
    void getAllWaterSourcesCallsGetAllWaterSources(){
        sut.getAllWaterSources();
        Mockito.verify(buildingDAO, Mockito.times(1)).getAllWaterSources();
    }
}
