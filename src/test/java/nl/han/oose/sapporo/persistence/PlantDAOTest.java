package nl.han.oose.sapporo.persistence;

import nl.han.oose.sapporo.dto.AllPlantDTO;
import nl.han.oose.sapporo.dto.PlantDTO;
import nl.han.oose.sapporo.persistence.datasource.ConnectionFactoryImp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class PlantDAOTest extends DAOTest {
    private PlantDAOImp sut = new PlantDAOImp();
    private final int AMOUNT_OF_PLANTS = 3;

    @Override
    void setfactory(ConnectionFactoryImp connectionFactoryImp) {
        sut.setConnectionFactory(connectionFactoryImp);
    }

    @Test
    void getAllPlantsGetsRightAmountOfPlants() {
        AllPlantDTO allplants = sut.getAllPlants();
        ArrayList<PlantDTO> plants = allplants.getPlants();
        Assertions.assertEquals(AMOUNT_OF_PLANTS, plants.size());
    }

    @Test
    void checkIfPlantFullGrownReturnsTrueWhenGrown() {
        PlantDTO grownPlant = new PlantDTO(1, "plant", 1, 1, 1, 1, 10);
        Assertions.assertTrue(sut.checkIfPlantFullGrown(grownPlant));
    }

    @Test
    void checkIfPlantFullGrownReturnsFalseWhenNotGrown() {
        PlantDTO notGrownPlant = new PlantDTO(1, "plant", 1, 500, 1, 1, 1);
        Assertions.assertFalse(sut.checkIfPlantFullGrown(notGrownPlant));
    }

    @Test
    void getProfitGetsRightProfit(){
        Assertions.assertEquals(20,sut.getProfit(1));
    }

}