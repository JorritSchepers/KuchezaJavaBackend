package nl.han.oose.sapporo.persistence;

import nl.han.oose.sapporo.dto.FarmDTO;
import nl.han.oose.sapporo.dto.PlantDTO;
import nl.han.oose.sapporo.dto.PlotDTO;
import nl.han.oose.sapporo.persistence.datasource.ConnectionFactoryImp;
import nl.han.oose.sapporo.persistence.exception.PersistenceException;
import nl.han.oose.sapporo.persistence.exception.PlotDoesNotExistException;
import nl.han.oose.sapporo.persistence.exception.PlotHasNotPlantException;
import nl.han.oose.sapporo.persistence.exception.PlotIsOccupiedException;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
    public PlotDTO getPlot(int PlotiD) {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("select * from plot where plotID = ?");
            statement.setInt(1, PlotiD);
            ResultSet resultSet = statement.executeQuery();
            PlotDTO plotDTO = null;

            while (resultSet.next()) {
                int iD = resultSet.getInt("plotID");
                ;
                int x = resultSet.getInt("x");
                int y = resultSet.getInt("y");
                float price = resultSet.getFloat("price");
                plotDTO = new PlotDTO(iD, x, y, price);
            }

            if(plotDTO == null) {
                throw new PlotDoesNotExistException();
            } else {
                return plotDTO;
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

    @Override
    public void createPlot(int x, int y){
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO plot (x, y, price, purchased) VALUE (?,?,?,?)");
            statement.setInt(1, x);
            statement.setInt(2, y);
            statement.setInt(3, 2);
            statement.setBoolean(4, false);
            statement.execute();
        } catch (SQLException e) {
            throw new PersistenceException();
        }
    }

    @Override
    public boolean plotHasPlant(int plotID) {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("select plantID from plot where plotID = ?");
            statement.setInt(1, plotID);
            ResultSet resultSet = statement.executeQuery();

            int plantID = 0;

            while (resultSet.next()) {
                plantID = resultSet.getInt("plantID");
            }
            if (plantID == 0) {
                throw new PlotHasNotPlantException();
            } else return true;
        } catch (SQLException e) {
            throw new PersistenceException();
        }
    }

    public void insertPlots(FarmDTO farmDTO) {
        try (Connection connection = connectionFactory.getConnection()) {
            for(PlotDTO plot: farmDTO.getPlots()) {
                PreparedStatement statement = connection.prepareStatement("INSERT INTO plot (x,y,price,purchased,farmID) VALUES(?,?,?,?,?);");
                statement.setInt(1,plot.getX());
                statement.setInt(2,plot.getY());
                statement.setFloat(3,plot.getPrice());
                statement.setBoolean(4,plot.isPurchased());
                statement.setInt(5,farmDTO.getFarmID());
                statement.execute();
            }
        } catch (SQLException e) {
            throw new PersistenceException();
        }
    }

    @Override
    public ArrayList<PlotDTO> getFarmPlots(int farmID) {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT plotID,x,y,price,animalID,waterManagerID,plantID,purchased FROM plot where farmID = ?");
            statement.setInt(1, farmID);
            ResultSet resultSet = statement.executeQuery();
            ArrayList<PlotDTO> plots = new ArrayList<>();

            while (resultSet.next()) {
                int ID = resultSet.getInt("plotID");
                int x = resultSet.getInt("x");
                int y = resultSet.getInt("y");
                int animalID = resultSet.getInt("animalID");
                int waterManagerID = resultSet.getInt("waterManagerID");
                int plantID = resultSet.getInt("plantID");
                float price = resultSet.getFloat("price");
                boolean purchased = resultSet.getBoolean("purchased");
                PlotDTO plot = new PlotDTO(ID, x, y, animalID, waterManagerID, plantID, price, purchased);
                plots.add(plot);
            }
            return plots;
        } catch (SQLException e) {
            throw new PersistenceException();
        }
    }
}