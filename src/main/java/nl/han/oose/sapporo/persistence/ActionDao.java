package nl.han.oose.sapporo.persistence;

import nl.han.oose.sapporo.persistence.datasource.ConnectionFactoryImp;
import nl.han.oose.sapporo.persistence.exception.PersistenceException;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ActionDao implements IActionDao {
    private ConnectionFactoryImp connectionFactory;

    @Inject
    public void setConnectionFactory(ConnectionFactoryImp connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public void insertAction(int id, int actionID, String affectedName, int water, int money) {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into actionPerPlayer (userID,actionID,affectedItem,currentWater,currentMoney) values(?,?,?,?,?)");
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, actionID);
            preparedStatement.setString(3, affectedName);
            preparedStatement.setInt(4, water);
            preparedStatement.setInt(5, money);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new PersistenceException();
        }
    }
}
