package nl.han.oose.sapporo.persistence;

import nl.han.oose.sapporo.dto.PlantDTO;
import nl.han.oose.sapporo.dto.PlotDTO;
import nl.han.oose.sapporo.persistence.datasource.ConnectionFactoryImp;
import nl.han.oose.sapporo.persistence.exception.PlotIsOccupiedException;
import nl.han.oose.sapporo.resource.exceptionmapper.PlotIsOccupiedMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.*;

class PlotDAOTest extends DAOTest {
    private PlotDAOImp sut = new PlotDAOImp();
    private final int PLOTID = 1;
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
    void checkIfAddPlantToPlotAddsPlant(){
        sut.addPlantToPlot(plant,PLOTID);
        Assertions.assertEquals(getPlantIDFromPlot(plant.getId()),getPlantIDFromPlot(PLOTID));
    }
}
