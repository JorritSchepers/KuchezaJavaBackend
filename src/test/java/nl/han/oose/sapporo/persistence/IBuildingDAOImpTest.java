package nl.han.oose.sapporo.persistence;

import nl.han.oose.sapporo.dto.AllWaterSourceDTO;
import nl.han.oose.sapporo.dto.WaterSourceDTO;
import nl.han.oose.sapporo.persistence.datasource.ConnectionFactoryImp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class IBuildingDAOImpTest extends DAOTest {
    private ArrayList<WaterSourceDTO> WATERSOURCES = new ArrayList<WaterSourceDTO>(
            List.of(new WaterSourceDTO(1,20,4000,750,"Well"),
                    new WaterSourceDTO(2,2,50000,500,"canal"))
    );
    private AllWaterSourceDTO ALLWATERSOURCEDTO = new AllWaterSourceDTO(WATERSOURCES);
    private IBuildingDAOImp sut = new IBuildingDAOImp();

    @Override
    void setFactory(ConnectionFactoryImp connectionFactoryImp) {
        sut.setConnectionFactory(connectionFactoryImp);
    }

    @Test
    public void getAllWaterSourcesReturnsAllWaterSourceDTO() {
        AllWaterSourceDTO result = sut.getAllWaterSources();
        Assertions.assertEquals(ALLWATERSOURCEDTO.getWaterSources().get(0),result.getWaterSources().get(0));
        Assertions.assertEquals(ALLWATERSOURCEDTO.getWaterSources().get(1),result.getWaterSources().get(1));
    }

    @Test
    public void getWaterSourceReturnsCorrectWaterSource() {
        WaterSourceDTO result = sut.getWaterSource(1);
        Assertions.assertEquals(ALLWATERSOURCEDTO.getWaterSources().get(0),result);
    }
}