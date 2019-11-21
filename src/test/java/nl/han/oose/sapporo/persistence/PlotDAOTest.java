package nl.han.oose.sapporo.persistence;

import nl.han.oose.sapporo.dto.PlantDTO;
import nl.han.oose.sapporo.dto.PlotDTO;
import nl.han.oose.sapporo.persistence.datasource.ConnectionFactoryImp;
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
    void addPlantToPlotAddsRightPlantToPlot() {
        Assertions.assertEquals(0, getPlantIDFromPlot(PLOTID));
        sut.addPlantToPlot(plant, PLOTID);
        Assertions.assertEquals(plant.getId(), getPlantIDFromPlot(PLOTID));
    }

    @Test
    void getPlotGetsRightPlot() {
        PlotDTO exceptedPlot = new PlotDTO(1, 1, 1, 10);
        PlotDTO plot = sut.getPlot(PLOTID);
        Assertions.assertEquals(exceptedPlot, plot);
    }
}
