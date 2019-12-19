package nl.han.oose.sapporo.persistence;

import nl.han.oose.sapporo.dto.AnimalDTO;
import nl.han.oose.sapporo.dto.PlantDTO;
import nl.han.oose.sapporo.persistence.datasource.ConnectionFactoryImp;
import nl.han.oose.sapporo.persistence.exception.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.*;

class PlotDAOTest extends DAOTest {
    private PlotDAOImp sut = new PlotDAOImp();
    private final int PLOT_ID = 1;
    private final int FULL_PLOT_ID = 2;
    private final int WATER_ADD = 20;
    private final PlantDTO PLANT = new PlantDTO(1, "Cabbage", 1, 1, 1, 1,100);
    private AnimalDTO ANIMAL = new AnimalDTO(1, "Cow", 10, 300, 10, 20, 1);

    @Override
    void setFactory(ConnectionFactoryImp connectionFactoryImp) {
        sut.setConnectionFactory(connectionFactoryImp);
    }

    private int getWaterFromPlot(int plotId) {
        int waterAvailable = 0;
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            PreparedStatement statement = connection.prepareStatement("SELECT waterAvailable FROM plot where plotID =?");
            statement.setInt(1, plotId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                waterAvailable = resultSet.getInt("waterAvailable");
            }
        } catch (SQLException ignored) {
        }
        return waterAvailable;
    }

    int getPlantIDFromPlot(int plotId) {
        int plantID = 0;
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            PreparedStatement statement = connection.prepareStatement("select plantID from plot where plotID = ?");
            statement.setInt(1, plotId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                plantID = resultSet.getInt("plantID");
            }
        } catch (SQLException ignored) {
        }
        return plantID;
    }

    int getAnimalIDFromPlot(int plotId) {
        int animalID = 0;
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            PreparedStatement statement = connection.prepareStatement("select animalID from plot where plotID = ?");
            statement.setInt(1, plotId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                animalID = resultSet.getInt("animalID");
            }
        } catch (SQLException ignored) {
        }
        return animalID;
    }

    private boolean plotIsEmpty(int plotId) {
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            int animalId = 0;
            int waterManagerId = 0;
            int plantId = 0;
            int age = 0;

            PreparedStatement statement = connection.prepareStatement("Select animalId, waterManagerId, plantID, objectAge from plot where plotID = ?");
            statement.setInt(1, plotId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                animalId = resultSet.getInt("animalId");
                waterManagerId = resultSet.getInt("waterManagerId");
                plantId = resultSet.getInt("plantID");
                age = resultSet.getInt("objectAge");
            }
            return ((animalId + waterManagerId + plantId) == 0);
        } catch (SQLException ignored) {
        }
        return false;
    }

    private int getAmountofPlots(int x, int y){
        int plotAmount = 0;
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            PreparedStatement statement = connection.prepareStatement("select count(plotID) as amount from plot where x =? and y=?");
            statement.setInt(1,x);
            statement.setInt(2,y);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                plotAmount = resultSet.getInt("amount");
            }
        } catch (SQLException ignored) {
        }
       return plotAmount;
    }

    private int getAge(int plotID){
        int age = 0;
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            PreparedStatement statement = connection.prepareStatement("select objectAge from plot where plotID = ?");
            statement.setInt(1,plotID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                age = resultSet.getInt("objectAge");
            }
            return age;

        } catch (SQLException ignored) {
        }
        return 0;
    }

    private String getStatus(int x, int y){
        String status = "";
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            PreparedStatement statement = connection.prepareStatement("select status from plot where x =? and y=?");
            statement.setInt(1,x);
            statement.setInt(2,y);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                status = resultSet.getString("status");
            }
            return status;

        } catch (SQLException ignored) {
        }
        return "";
    }

    private boolean plotIsPurchased(int plotID) {
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            PreparedStatement statement = connection.prepareStatement("select purchased from plot where plotID = ?");
            statement.setInt(1, plotID);
            ResultSet resultSet = statement.executeQuery();
            boolean plotPurchased = false;

            while (resultSet.next()) {
                plotPurchased = resultSet.getBoolean("purchased");
            }

            return plotPurchased;
        } catch (SQLException ignored) {
        }
        return false;
    }

    @Test
    void checkIfPlotIsEmptyReturnsTrueWhenEmpty() {
        Assertions.assertTrue(sut.checkIfPlotIsEmpty(1));
    }

    @Test
    void checkIfPlotIsNotFilledToTheMaxThrowsExceptionPlotHadMaximumWater() {
        Assertions.assertDoesNotThrow( () -> {
            sut.editWaterAvailable(WATER_ADD, PLOT_ID);
        });
    }

    @Test
    void checkIfPlotIsEmptyThrowsExceptionWhenNotEmpty() {
        Assertions.assertThrows(PlotIsOccupiedException.class, () -> {
            sut.checkIfPlotIsEmpty(2);
        });
    }

    @Test
    void plotIsPurchasedReturnsTrueWhenPurchased() {
        Assertions.assertTrue(sut.plotIsPurchased(1));
    }

    @Test
    void checkIfAddPlantToPlotAddsPlant() {
        sut.addPlantToPlot(PLANT, PLOT_ID);
        Assertions.assertEquals(getPlantIDFromPlot(PLANT.getId()), getPlantIDFromPlot(PLOT_ID));
    }

    @Test
    void getPlotThrowsNoExceptionWhenPlotDoesExist() {
        Assertions.assertDoesNotThrow(() -> {
            sut.getPlot(PLOT_ID);
        });
    }

    @Test
    void getPlotThrowsExpcetionWhenPlotDoesNotExist() {
        int fakePlotID = 4;
        Assertions.assertThrows(PlotDoesNotExistException.class, () -> {
            sut.getPlot(fakePlotID);
        });
    }

    @Test
    void plotHasPlantReturnsTrueWhenTrue() {
        Assertions.assertTrue(sut.plotHasPlant(FULL_PLOT_ID));
    }

    @Test
    void plotHasPlantThrowsExceptionWhenFalse() {
        Assertions.assertThrows(PlotHasNoPlantException.class, () -> {
            sut.plotHasPlant(PLOT_ID);
        });
    }

    @Test
    void removeObjectsFromPlotRemovesObject() {
        Assertions.assertFalse(plotIsEmpty(FULL_PLOT_ID));
        sut.removeObjectsFromPlot(FULL_PLOT_ID);
        Assertions.assertTrue(plotIsEmpty(FULL_PLOT_ID));
    }

    @Test
    void checkIfPurchasePlotPurchasedPlot() {
        final int PURCHASE_PLOT_ID = 3;
        Assertions.assertFalse(plotIsPurchased(PURCHASE_PLOT_ID));
        sut.purchasePlot(PURCHASE_PLOT_ID);
        Assertions.assertTrue(plotIsPurchased(PURCHASE_PLOT_ID));
    }

    @Test
    void getFarmPlotsGetsRightAmountOfPlots(){
        int AMOUNT_OF_PLOTS = 3;
        int FARM_ID = 1;
        Assertions.assertEquals(sut.getFarmPlots(FARM_ID).size(),AMOUNT_OF_PLOTS);
    }

    @Test
    void checkIfPlotIsEmptyThrowsExceptionPlotHasMaximumWater() {
        Assertions.assertThrows(PlotIsOccupiedException.class, () -> {
            sut.checkIfPlotIsEmpty(2);
        });
    }

    @Test
    void increaseWaterIncreasesWaterWithRightAmount() {
        int extraWater = WATER_ADD;
        int oldWater = getWaterFromPlot(PLOT_ID);
        sut.editWaterAvailable(extraWater, PLOT_ID);
        int newWater = getWaterFromPlot(PLOT_ID);
        Assertions.assertEquals((oldWater + extraWater), newWater);
    }

    @Test
    void changeStatusChangesStatusInDatabase() {
        String expected = "Dead";
        sut.changeStatus(1, "Dead");
        Assertions.assertEquals(expected,getStatus(1,1));
    }

    @Test
    void checkIfAddAnimalToPlotAddsAnimal() {
        final int ANIMAL_PLOT_ID = 3;
        sut.addAnimalToPlot(ANIMAL, ANIMAL_PLOT_ID);
        Assertions.assertEquals(ANIMAL.getId(), getAnimalIDFromPlot(ANIMAL_PLOT_ID));
    }

    @Test
    void plotHasAnimalReturnsTrueWhenTrue() {
        int PLOT_WITH_ANIMAL_ID = 3;
        Assertions.assertTrue(sut.plotHasAnimal(PLOT_WITH_ANIMAL_ID));
    }

    @Test
    void plotHasAnimalThrowsExceptionWhenFalse() {
        Assertions.assertThrows(PlotHasNoAnimalException.class, () -> {
            sut.plotHasAnimal(PLOT_ID);
        });
    }

    @Test
    void replaceAnimalsOnAllPlotsReplacesAnimal(){
        final int DELETEID =2;
        final int REPLACEID =1;
        final int AFFECTEDPLOTID = 3;
        Assertions.assertEquals(getAnimalIDFromPlot(AFFECTEDPLOTID),DELETEID);
        sut.replaceAnimalsOnAllPlots(DELETEID,REPLACEID);
        Assertions.assertEquals(getAnimalIDFromPlot(AFFECTEDPLOTID),REPLACEID);
    }
}
