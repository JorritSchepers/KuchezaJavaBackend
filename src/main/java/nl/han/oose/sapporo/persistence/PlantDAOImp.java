package nl.han.oose.sapporo.persistence;

import nl.han.oose.sapporo.dto.AllPlantDTO;
import nl.han.oose.sapporo.dto.PlantDTO;
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
public class PlantDAOImp implements IPlantDAO{
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
    public boolean checkIfPlantFullGrown(PlantDTO plantDTO) {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("select growingTime from plant where plantID = ?");
            statement.setInt(1,plantDTO.getId());
            ResultSet resultSet = statement.executeQuery();
            int neededGrowingTime = 0;
            while (resultSet.next()) {
                neededGrowingTime = resultSet.getInt("growingTime");
            }
            return (plantDTO.getAge() >= neededGrowingTime);
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
            int age = 0;
            plants.add(new PlantDTO(id,name,waterUsage,growingTime,profit,purchasePrice,age));
        }
        return new AllPlantDTO(plants);
    }
}
