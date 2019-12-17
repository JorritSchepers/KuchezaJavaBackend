package nl.han.oose.sapporo.persistence;

import nl.han.oose.sapporo.dto.AllAnimalDTO;
import nl.han.oose.sapporo.dto.AnimalDTO;
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
public class AnimalDAOImp implements IAnimalDAO {
    private ConnectionFactoryImp connectionFactory;

    @Inject
    public void setConnectionFactory(ConnectionFactoryImp connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public AllAnimalDTO getAllAnimals() {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("select * from animal");
            ResultSet resultSet = statement.executeQuery();
            return makeAllAnimalDTO(resultSet);
        } catch (SQLException e) {
            throw new PersistenceException();
        }
    }

    private AllAnimalDTO makeAllAnimalDTO(ResultSet resultSet) throws SQLException {
        ArrayList<AnimalDTO> animals = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("animalId");
            String name = resultSet.getString("name");
            int waterUsage = resultSet.getInt("waterUsage");
            int maximumWater = resultSet.getInt("maximumWater");
            int productionTime = resultSet.getInt("productionTime");
            float profit = resultSet.getFloat("profit");
            float purchasePrice = resultSet.getFloat("purchasePrice");
            animals.add(new AnimalDTO(id,name,waterUsage, maximumWater,productionTime,profit,purchasePrice));
        }
        return new AllAnimalDTO(animals);
    }
}
