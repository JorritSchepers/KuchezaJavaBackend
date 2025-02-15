package nl.han.oose.sapporo.persistence;

import nl.han.oose.sapporo.dto.AllPlantDTO;
import nl.han.oose.sapporo.dto.PlantDTO;
import nl.han.oose.sapporo.dto.PlotDTO;
import nl.han.oose.sapporo.persistence.datasource.ConnectionFactoryImp;
import nl.han.oose.sapporo.persistence.exception.PersistenceException;

import javax.enterprise.inject.Default;
import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Default
public class PlantDAOImp implements IPlantDAO {
    private ConnectionFactoryImp connectionFactory;

    @Inject
    public void setConnectionFactory(ConnectionFactoryImp connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public AllPlantDTO getAllPlants() {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("select * from plant");
            ResultSet resultSet = statement.executeQuery();
            return makeAllPlantDTO(resultSet);
        } catch (SQLException e) {
            throw new PersistenceException();
        }
    }

    @Override
    public boolean checkIfPlantFullGrown(PlotDTO plotDTO) {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("select growingTime from plant where plantID = ?");
            statement.setInt(1, plotDTO.getPlantID());
            ResultSet resultSet = statement.executeQuery();
            int neededGrowingTime = 0;
            while (resultSet.next()) {
                neededGrowingTime = resultSet.getInt("growingTime");
            }
            return (plotDTO.getAge() >= neededGrowingTime);
        } catch (SQLException e) {
            throw new PersistenceException();
        }
    }

    public int getProfit(int id) {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("select profit from plant where plantID = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            int profit = 0;
            while (resultSet.next()) {
                profit = resultSet.getInt("profit");
            }
            return profit;
        } catch (SQLException e) {
            throw new PersistenceException();
        }
    }

    @Override
    public int getMaximumWater(int id) {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("select maximumWater from plant where plantID = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            int maximumWater = 0;
            while (resultSet.next()) {
                maximumWater = resultSet.getInt("maximumWater");
            }
            return maximumWater;
        } catch (SQLException e) {
            throw new PersistenceException();
        }
    }

    public void deletePlant(int plantIDToDelete) {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("delete from plant where plantID = ?");
            statement.setInt(1, plantIDToDelete);
            statement.execute();
        } catch (SQLException ignored) {
            throw new PersistenceException();
        }
    }

    @Override
    public String getName(int plantID) {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("select name from plant where plantID = ?");
            statement.setInt(1, plantID);
            ResultSet resultSet = statement.executeQuery();
            String name = null;
            while (resultSet.next()) {
                name = resultSet.getString("name");
            }
            return name;
        } catch (SQLException e) {
            throw new PersistenceException();
        }
    }

    private AllPlantDTO makeAllPlantDTO(ResultSet resultSet) throws SQLException {
        ArrayList<PlantDTO> plants = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("plantID");
            String name = resultSet.getString("name");
            int waterUsage = resultSet.getInt("waterUsage");
            int growingTime = resultSet.getInt("growingTime");
            float profit = resultSet.getFloat("profit");
            float purchasePrice = resultSet.getFloat("purchasePrice");
            int maximumWater = resultSet.getInt("maximumWater");
            plants.add(new PlantDTO(id, name, waterUsage, growingTime, profit, purchasePrice, maximumWater));
        }
        return new AllPlantDTO(plants);
    }
}
