package nl.han.oose.sapporo.persistence;

import nl.han.oose.sapporo.dto.FarmDTO;
import nl.han.oose.sapporo.dto.PlantDTO;
import nl.han.oose.sapporo.dto.PlotDTO;
import nl.han.oose.sapporo.persistence.datasource.ConnectionFactoryImp;
import nl.han.oose.sapporo.persistence.exception.*;

import javax.inject.Inject;
import java.sql.*;
import java.util.ArrayList;

public class PlotDAOImp implements IPlotDAO {
    public static final int START_WATER = 25;
    private ConnectionFactoryImp connectionFactory;

    @Inject
    public void setConnectionFactory(ConnectionFactoryImp connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public void addPlantToPlot(PlantDTO plantDTO, int plotID) {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("update plot set plantID = ?, waterAvailable = ? where plotID = ? ");
            statement.setInt(1, plantDTO.getID());
            statement.setInt(2, START_WATER);
            statement.setInt(3, plotID);
            statement.execute();
        } catch (SQLException e) {
            throw new PersistenceException();
        }
    }

    @Override
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
    public boolean plotIsPurchased(int plotID) {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("select purchased from plot where plotID = ?");
            statement.setInt(1, plotID);
            ResultSet resultSet = statement.executeQuery();
            boolean plotPurchased = false;

            while (resultSet.next()) {
                plotPurchased = resultSet.getBoolean("purchased");
            }

            return plotPurchased;
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
                int waterAvailable = resultSet.getInt("waterAvailable");
                boolean purchased = resultSet.getBoolean("purchased");
                plotDTO = new PlotDTO(iD, x, y, price, purchased, waterAvailable);
                plotDTO.setAge(resultSet.getInt("objectAge"));
                plotDTO.setPlantID(resultSet.getInt("plantID"));
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
            PreparedStatement statement = connection.prepareStatement("update plot set animalId = null, waterManagerId = null, plantID = null, objectAge = 0 where plotID = ?");
            statement.setInt(1, plotID);
            statement.execute();
        } catch (SQLException e) {
            throw new PersistenceException();
        }
    }

    @Override
    public void purchasePlot(int plotID) {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("update plot set purchased = 1 where plotID = ?");
            statement.setInt(1, plotID);
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
            PreparedStatement statement = connection.prepareStatement("SELECT plotID,x,y,price,animalID,waterManagerID,plantID,purchased,objectAge,waterAvailable FROM plot where farmID = ?");
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
                int waterAvailable = resultSet.getInt("waterAvailable");
                boolean purchased = resultSet.getBoolean("purchased");
                int age = resultSet.getInt("objectAge");
                PlotDTO plot = new PlotDTO(ID, x, y, animalID, waterManagerID, plantID, price, purchased,age, waterAvailable);
                plots.add(plot);
            }
            return plots;
        } catch (SQLException e) {
            throw new PersistenceException();
        }
    }

    @Override
    public void updateAge(int plotID, int age) {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE plot SET objectAge = ? WHERE plotID = ?");
            statement.setInt(1,age);
            statement.setInt(2,plotID);
            statement.execute();
        } catch (SQLException e) {
            throw new PersistenceException();
        }
    }
    
    public void increaseWaterAvailable(int amount, int plotID) {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement
                    ("update plot set waterAvailable = waterAvailable+? where plotID = ?");
            statement.setInt(1,amount);
            statement.setInt(2,plotID);
            statement.execute();
        } catch (SQLException e) {
            throw new PlotHasMaximumWaterException();
        }
    }
}