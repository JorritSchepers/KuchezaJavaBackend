package nl.han.oose.sapporo.persistence;

import nl.han.oose.sapporo.dto.PlantDTO;
import nl.han.oose.sapporo.dto.PlotDTO;
import nl.han.oose.sapporo.persistence.datasource.ConnectionFactoryImp;
import nl.han.oose.sapporo.persistence.exception.PersistenceException;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlotDAOImp implements IPlotDAO {
    private ConnectionFactoryImp connectionFactory;

    @Inject
    public void setConnectionFactory(ConnectionFactoryImp connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public void addPlantToPlot(PlantDTO plantDTO, int plotID) {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("update plot set plantID = ? where plotID = ? ");
            statement.setInt(1, plantDTO.getId());
            statement.setInt(2, plotID);
            statement.execute();
        } catch (SQLException e) {
            throw new PersistenceException();
        }
    }

    @Override
    public PlotDTO getPlot(int PlotiD) {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("select * from plot where plotID = ?");
            statement.setInt(1, PlotiD);
            ResultSet resultSet = statement.executeQuery();
            PlotDTO plotDTO = null;

            while (resultSet.next()) {
                int iD = resultSet.getInt("plotID");
                int x = resultSet.getInt("x");
                int y = resultSet.getInt("y");
                float price = resultSet.getFloat("price");
                plotDTO = new PlotDTO(iD, x, y, price);
            }
            return plotDTO;
        } catch (SQLException e) {
            throw new PersistenceException();
        }
    }
}