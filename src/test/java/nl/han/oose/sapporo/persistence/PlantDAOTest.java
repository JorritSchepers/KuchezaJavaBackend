package nl.han.oose.sapporo.persistence;

import nl.han.oose.sapporo.dto.AllPlantDTO;
import nl.han.oose.sapporo.dto.PlantDTO;
import nl.han.oose.sapporo.dto.PlotDTO;
import nl.han.oose.sapporo.persistence.datasource.ConnectionFactoryImp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.ArrayList;

class PlantDAOTest extends DAOTest {
    private PlantDAOImp sut = new PlantDAOImp();
    private final PlotDTO PLOT_WITH_GROWN_PLANT = new PlotDTO(1, 1, 1, 1, 0, 1, 0, 100);
    private final PlotDTO PLOT_WITHOUT_GROWN_PLANT = new PlotDTO(2, 1, 1, 1, 0, 1, 0, 0);

    @Override
    void setFactory(ConnectionFactoryImp connectionFactoryImp) {
        sut.setConnectionFactory(connectionFactoryImp);
    }

    @Test
    void getAllPlantsGetsRightAmountOfPlants() {
        int AMOUNT_OF_PLANTS = 3;
        AllPlantDTO allPlants = sut.getAllPlants();
        ArrayList<PlantDTO> plants = allPlants.getPlants();
        Assertions.assertEquals(AMOUNT_OF_PLANTS, plants.size());
    }

    @Test
    void checkIfPlantFullGrownReturnsTrueWhenGrown() {
        Assertions.assertTrue(sut.checkIfPlantFullGrown(PLOT_WITH_GROWN_PLANT));
    }

    @Test
    void checkIfPlantFullGrownReturnsFalseWhenNotGrown() {
        Assertions.assertFalse(sut.checkIfPlantFullGrown(PLOT_WITHOUT_GROWN_PLANT));
    }

    @Test
    void getProfitGetsRightProfit(){
        Assertions.assertEquals(20,sut.getProfit(1));
    }

    @Test
    void deletePlantRemovesOneRowInDB() {
        int oldAmount = getAmountOfPlants();
        sut.deletePlant(1);
        int newAmount = getAmountOfPlants();
        Assertions.assertEquals(oldAmount-1, newAmount);
    }

    @Test
    void getMaximumWaterReturnsCorrectAmount() {
        Assertions.assertEquals(200, sut.getMaximumWater(2));
    }

    @Test
    void getNameReturnsRightName(){
        final int PLANTID1 = 1;
        final String PLANNAME1 = "Cabbage";
        Assertions.assertEquals(sut.getName(PLANTID1),PLANNAME1);
    }

    private int getAmountOfPlants() {
        int totalPlants = 0;
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            PreparedStatement statement = connection.prepareStatement("select count(plantID) as amount from plant");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                totalPlants = resultSet.getInt("amount");
            }
        } catch (SQLException ignored) {
        }
        return totalPlants;
    }
}
