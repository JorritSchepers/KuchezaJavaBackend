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
    private PlantDTO plant = new PlantDTO(1, "Cabbage", 1, 1, 1, 1, 1);

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
            PreparedStatement statement = connection.prepareStatement("Select animalId, waterManagerId, plantID from plot where plotID = ?");
            statement.setInt(1, plotId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                animalId = resultSet.getInt("animalId");
                waterManagerId = resultSet.getInt("waterManagerId");
                plantId = resultSet.getInt("plantID");
            }
            return (full + animalId + waterManagerId + plantId == 0);
        } catch (SQLException ignored) { }
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
    void plotHasPlantReturnsTrueWhenTrue(){
        Assertions.assertTrue(sut.plotHasPlant(FULLPLOTID));
    }

    @Test
    void plotHasPlantThrowsExceptionWhenFalse(){
        Assertions.assertThrows(PlotHasNotPlantException.class, () -> {
            sut.plotHasPlant(PLOTID);
        });
    }

    @Test
    void removeObjectsFromPlotRemovesObject(){
        Assertions.assertFalse(plotIsEmpty(FULLPLOTID));
        sut.removeObjectsFromPlot(FULLPLOTID);
        Assertions.assertTrue(plotIsEmpty(FULLPLOTID));
    }
}