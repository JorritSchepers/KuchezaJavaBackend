package nl.han.oose.sapporo.persistence;

import nl.han.oose.sapporo.dto.FarmDTO;
import nl.han.oose.sapporo.dto.UserDTO;
import nl.han.oose.sapporo.persistence.datasource.ConnectionFactoryImp;
import nl.han.oose.sapporo.persistence.exception.PersistenceException;

import javax.inject.Inject;
import java.sql.*;

public class FarmDAOIMP implements IfarmDAO {
    private ConnectionFactoryImp connectionFactory;

    @Inject
    public void setConnectionFactory(ConnectionFactoryImp connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public FarmDTO getFarm(UserDTO user) {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * from farm where ownerID = ?");
            statement.setInt(1, user.getId());

            FarmDTO farm = null;
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int farmID = resultSet.getInt("farmID");
                int ownerID = resultSet.getInt("ownerID");
                farm = new FarmDTO(farmID,ownerID);
            }
            return farm;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new PersistenceException();
        }
    }
}
