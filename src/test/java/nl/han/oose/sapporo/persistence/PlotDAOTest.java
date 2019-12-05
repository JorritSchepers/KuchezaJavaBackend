package nl.han.oose.sapporo.persistence;

import nl.han.oose.sapporo.dto.PlantDTO;
import nl.han.oose.sapporo.persistence.datasource.ConnectionFactoryImp;
import nl.han.oose.sapporo.persistence.exception.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.*;

class PlotDAOTest extends DAOTest {
    private PlotDAOImp sut = new PlotDAOImp();
    private final int PLOT_ID = 1;
    private final int FULL_PLOT_ID = 2;
    private PlantDTO plant = new PlantDTO(1, "Cabbage", 1, 1, 1, 1, 1);

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
            int animalID = 0;
            int waterManagerID = 0;
            int plantID = 0;
            PreparedStatement statement = connection.prepareStatement("Select animalId, waterManagerId, plantID from plot where plotID = ?");
            statement.setInt(1, plotId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                animalID = resultSet.getInt("animalID");
                waterManagerID = resultSet.getInt("waterManagerID");
                plantID = resultSet.getInt("plantID");
            }
            return (full + animalID + waterManagerID + plantID == 0);
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
    void checkIfAddPlantToPlotAddsPlant() {
        sut.addPlantToPlot(plant, PLOT_ID);
        Assertions.assertEquals(getPlantIDFromPlot(plant.getID()), getPlantIDFromPlot(PLOT_ID));
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
        Assertions.assertThrows(PlotHasNotPlantException.class, () -> {
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
    void checkIfPlotHasBeenFilledToTheMaxThrowsExceptionPlotHadMaximumWater() {
        Assertions.assertThrows(PlotHasMaximumWaterException.class, () -> {
            sut.increaseWaterAvailable(100, FULL_PLOT_ID);
        });
    }

    @Test
    void checkIfPlotIsNotFilledToTheMaxThrowsExceptionPlotHadMaximumWater() {
        Assertions.assertDoesNotThrow( () -> {
            sut.increaseWaterAvailable(20, FULL_PLOT_ID);
        });
    }

    @Test
    void increaseWaterIncreasesWaterWithRightAmount() {
        int extraWater = 10;
        int oldWater = getWaterFromPlot(FULL_PLOT_ID);
        sut.increaseWaterAvailable(extraWater, FULL_PLOT_ID);
        int newWater = getWaterFromPlot(FULL_PLOT_ID);
        Assertions.assertEquals((oldWater + extraWater), newWater);
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
}
