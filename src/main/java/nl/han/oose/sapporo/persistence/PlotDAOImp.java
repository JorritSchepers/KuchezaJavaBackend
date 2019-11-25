package nl.han.oose.sapporo.persistence;

import nl.han.oose.sapporo.dto.PlantDTO;
import nl.han.oose.sapporo.dto.PlotDTO;
import nl.han.oose.sapporo.persistence.datasource.ConnectionFactoryImp;
import nl.han.oose.sapporo.persistence.exception.PersistenceException;
import nl.han.oose.sapporo.persistence.exception.PlotIsOccupiedException;
import nl.han.oose.sapporo.persistence.exception.plotDoesNotExistException;


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

            if (!resultSet.next()){
                throw new plotDoesNotExistException();
            }
            while (resultSet.next()) {
                int iD = resultSet.getInt("plotID");
                int x = resultSet.getInt("x");
                int y = resultSet.getInt("y");
                int animalId = resultSet.getInt("animalId");
                int waterManagerId = resultSet.getInt("waterManagerId");
                int plantID = resultSet.getInt("plantID");
                float price = resultSet.getFloat("price");
                plotDTO = new PlotDTO(iD, x, y, animalId, waterManagerId, plantID, price);
            }
            return plotDTO;
        } catch (SQLException e) {
            throw new PersistenceException();
        }
    }

    public boolean checkIfPlotIsEmpty(int plotID) {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("select animalID,watermanagerID,plantID from plot where plotID = ?");
            statement.setInt(1, plotID);
            ResultSet resultSet = statement.executeQuery();
            int plottaken = 0;
            int animalID = 0;
            int watermanagerID = 0;
            int plantID = 0;

            while (resultSet.next()) {
                animalID = resultSet.getInt("animalID");
                watermanagerID = resultSet.getInt("watermanagerID");
                plantID = resultSet.getInt("plantID");
            }

            plottaken += animalID;
            plottaken += watermanagerID;
            plottaken += plantID;

            if (plottaken == 0) {
                return true;
            } else {
                throw new PlotIsOccupiedException();
            }

        } catch (SQLException e) {
            throw new PersistenceException();
        }
    }

    @Override
    public void removeObjectsFromPlot(int plotID) {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("update plot set animalId = null, waterManagerId = null, plantID = null where plotID = ?");
            statement.setInt(1, plotID);
            statement.execute();
        } catch (SQLException e) {
            throw new PersistenceException();
        }
    }
}