package nl.han.oose.sapporo.persistence;

import nl.han.oose.sapporo.dto.PlantDTO;
import nl.han.oose.sapporo.persistence.datasource.ConnectionFactoryImp;
import nl.han.oose.sapporo.persistence.exception.PlotDoesNotExistException;
import nl.han.oose.sapporo.persistence.exception.PlotHasNotPlantException;
import nl.han.oose.sapporo.persistence.exception.PlotIsOccupiedException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.*;

class PlotDAOTest extends DAOTest {
    private PlotDAOImp sut = new PlotDAOImp();
    private final int PLOTID = 1;
    private final int FULLPLOTID = 2;
    private final int FARMID = 1;
    private final int PURCHASE_PLOT_ID = 3;
    private PlantDTO plant = new PlantDTO(1, "Cabbage", 1, 1, 1, 1);

    @Override
    void setFactory(ConnectionFactoryImp connectionFactoryImp) {
        sut.setConnectionFactory(connectionFactoryImp);
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

    private boolean plotIsEmpty(int plotId) {
        int full = 0;
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            int animalId = 0;
            int waterManagerId = 0;
            int plantId = 0;
            int age = 0;
            PreparedStatement statement = connection.prepareStatement("Select animalId, waterManagerId, plantID, age from plot where plotID = ?");
            statement.setInt(1, plotId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                animalId = resultSet.getInt("animalId");
                waterManagerId = resultSet.getInt("waterManagerId");
                plantId = resultSet.getInt("plantID");
                age = resultSet.getInt("age");
            }
            return (full + animalId + waterManagerId + plantId + age == 0);
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

    private int getAge(int x, int y){
        int age = 0;
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            PreparedStatement statement = connection.prepareStatement("select objectAge from plot where x =? and y=?");
            statement.setInt(1,x);
            statement.setInt(2,y);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                age = resultSet.getInt("objectAge");
            }
        } catch (SQLException ignored) {
        }
        return age;
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
        sut.addPlantToPlot(plant, PLOTID);
        Assertions.assertEquals(getPlantIDFromPlot(plant.getID()), getPlantIDFromPlot(PLOTID));
    }

    @Test
    void getPlotThrowsNoExceptionWhenPlotDoesExist() {
        Assertions.assertDoesNotThrow(() -> {
            sut.getPlot(PLOTID);
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
        Assertions.assertTrue(sut.plotHasPlant(FULLPLOTID));
    }

    @Test
    void plotHasPlantThrowsExceptionWhenFalse() {
        Assertions.assertThrows(PlotHasNotPlantException.class, () -> {
            sut.plotHasPlant(PLOTID);
        });
    }

    @Test
    void removeObjectsFromPlotRemovesObject() {
        Assertions.assertFalse(plotIsEmpty(FULLPLOTID));
        sut.removeObjectsFromPlot(FULLPLOTID);
        Assertions.assertTrue(plotIsEmpty(FULLPLOTID));
    }

    @Test
    void checkIfPurchasePlotPurchasedPlot() {
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

    private int getAmountOfPlots(int x, int y){
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

    @Test
    void updateAgeUpdatesAge() {
        Assertions.assertEquals(10,getAge(1,1));
        sut.updateAge(1,1000);
        Assertions.assertEquals(1000,getAge(1,1));
    }
}
