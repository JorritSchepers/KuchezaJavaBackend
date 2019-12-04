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
    private PlantDTO plant = new PlantDTO(1, "Cabbage", 1, 1, 1, 1);

    @Override
    void setfactory(ConnectionFactoryImp connectionFactoryImp) {
        sut.setConnectionFactory(connectionFactoryImp);
    }

    int getPlantIDFromPlot(int plotId) {
        int plantId = 0;

        try (Connection connection = DriverManager.getConnection(dbUrl)) {
            PreparedStatement statement = connection.prepareStatement("select plantID from plot where plotID = ?");
            statement.setInt(1, plotId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                plantId = resultSet.getInt("plantID");
            }
        } catch (SQLException ignored) {
        }
        return plantId;
    }

    private boolean plotIsEmpty(int plotId) {
        int full = 0;
        try (Connection connection = DriverManager.getConnection(dbUrl)) {
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
        try (Connection connection = DriverManager.getConnection(dbUrl)) {
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
        try (Connection connection = DriverManager.getConnection(dbUrl)) {
            PreparedStatement statement = connection.prepareStatement("select age from plot where x =? and y=?");
            statement.setInt(1,x);
            statement.setInt(2,y);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                age = resultSet.getInt("age");
            }
        } catch (SQLException ignored) {
        }
        return age;
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
    void checkIfAddPlantToPlotAddsPlant() {
        sut.addPlantToPlot(plant, PLOTID);
        Assertions.assertEquals(getPlantIDFromPlot(plant.getID()), getPlantIDFromPlot(PLOTID));
    }

    @Test
    void getPlotThrowsNoExpcetionWhenPlotDoesExist() {
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
    void getFarmPlotsGetsRightAmountOfPlots(){
        int AMOUNTOFPLOTS = 3;
        Assertions.assertEquals(sut.getFarmPlots(FARMID).size(),AMOUNTOFPLOTS);
    }

    @Test
    void updateAgeUpdatesAge() {
        Assertions.assertEquals(10,getAge(1,1));
        sut.updateAge(1,1000);
        Assertions.assertEquals(1000,getAge(1,1));
    }
}