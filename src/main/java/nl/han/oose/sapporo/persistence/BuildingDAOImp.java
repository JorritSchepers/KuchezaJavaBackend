package nl.han.oose.sapporo.persistence;

import nl.han.oose.sapporo.dto.AllWaterSourceDTO;
import nl.han.oose.sapporo.dto.WaterSourceDTO;
import nl.han.oose.sapporo.persistence.datasource.ConnectionFactoryImp;
import nl.han.oose.sapporo.persistence.exception.PersistenceException;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BuildingDAOImp implements BuildingDAO {
    private ConnectionFactoryImp connectionFactory;

    @Inject
    public void setConnectionFactory(ConnectionFactoryImp connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public AllWaterSourceDTO getAllWaterSources() {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("select * from watersource");
            ResultSet resultSet = statement.executeQuery();
            return makeAllWaterSourceDTO(resultSet);
        } catch (SQLException e) {
            throw new PersistenceException();
        }
    }

    @Override
    public WaterSourceDTO getWaterSource(int waterSourceID) {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("select * from watersource where waterSourceId = ?");
            statement.setInt(1,waterSourceID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new WaterSourceDTO(resultSet.getInt("waterSourceId"),
                        resultSet.getInt("waterYield"),
                        resultSet.getInt("maximumWater"),
                        resultSet.getInt("purchasePrice"),
                        resultSet.getString("name"));
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new PersistenceException();
        }
    }

    private AllWaterSourceDTO makeAllWaterSourceDTO(ResultSet resultSet) throws SQLException {
        ArrayList<WaterSourceDTO> waterSources = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("waterSourceId");
            int waterYield = resultSet.getInt("waterYield");
            int maximumWater = resultSet.getInt("maximumWater");
            int purchasePrice = resultSet.getInt("purchasePrice");
            String name = resultSet.getString("name");
            waterSources.add(new WaterSourceDTO(id,waterYield,maximumWater,purchasePrice,name));
        }
        return new AllWaterSourceDTO(waterSources);
    }
}
