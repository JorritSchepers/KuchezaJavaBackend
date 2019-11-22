package nl.han.oose.sapporo.persistence;

import nl.han.oose.sapporo.dto.FarmDTO;
import nl.han.oose.sapporo.dto.UserDTO;
import nl.han.oose.sapporo.persistence.datasource.ConnectionFactoryImp;
import nl.han.oose.sapporo.persistence.exception.PersistenceException;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FarmDAOImp implements IFarmDAO {
    private ConnectionFactoryImp connectionFactory;

    @Inject
    public void setConnectionFactoryImp(ConnectionFactoryImp connectionFactory){
        this.connectionFactory = connectionFactory;
    }

    @Override
    public void addFarm(FarmDTO farmDTO, UserDTO userDTO){
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO farm (ownerID) VALUES (?)");
            statement.setInt(1, userDTO.getId());

            statement.execute();
        } catch (SQLException e) {
            throw new PersistenceException();
        }
    }

}
