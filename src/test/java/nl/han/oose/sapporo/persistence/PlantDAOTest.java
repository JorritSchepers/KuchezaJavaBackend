package nl.han.oose.sapporo.persistence;

import nl.han.oose.sapporo.dto.AllPlantDTO;
import nl.han.oose.sapporo.dto.PlantDTO;
import nl.han.oose.sapporo.persistence.datasource.ConnectionFactoryImp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.util.ArrayList;

public class PlantDAOTest extends DAOTest {
    private PlantDAOIMP sut = new PlantDAOIMP();
    private final int AMOUNT_OF_PLANTS = 3;

    @Override
    void setfactory(ConnectionFactoryImp connectionFactoryImp) {
        sut.setConnectionFactory(connectionFactoryImp);
    }

    @Test
    public void getAllPlantsGetsRightAmountOfPlants() {
        AllPlantDTO allplants = sut.getAllPlants();
        ArrayList<PlantDTO> plants = allplants.getPlants();
        Assertions.assertEquals(AMOUNT_OF_PLANTS, plants.size());
    }

}