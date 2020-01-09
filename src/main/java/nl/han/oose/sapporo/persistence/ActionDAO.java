package nl.han.oose.sapporo.persistence;

import nl.han.oose.sapporo.dto.ActionDTO;
import nl.han.oose.sapporo.dto.AllActionsDTO;
import nl.han.oose.sapporo.persistence.datasource.ConnectionFactoryImp;
import nl.han.oose.sapporo.persistence.exception.PersistenceException;

import javax.inject.Inject;
import java.sql.*;
import java.util.ArrayList;

public class ActionDAO implements IActionDao {
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

    @Override
    public AllActionsDTO getUserActions(int userID) {
        ArrayList<ActionDTO> actions = new ArrayList<>();
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from actionPerPlayer inner join action on actionPerPlayer.actionID = action.actionID where userID = ? order by dateOfAction");
            preparedStatement.setInt(1, userID);
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                 int user  = resultSet.getInt("userID");
                 int actionID = resultSet.getInt("actionID");
                 Timestamp dateOfAction = resultSet.getTimestamp("dateOfAction");
                 String affectedItem = resultSet.getString("affectedItem");
                 int currentWater  = resultSet.getInt("currentWater");
                 int currentMoney = resultSet.getInt("currentMoney");
                 String actionText = resultSet.getString("actionText");
                 actions.add(new ActionDTO(user,actionID,dateOfAction,affectedItem,currentWater,currentMoney,actionText));
            }
            return new AllActionsDTO(actions);
        } catch (SQLException e) {
            throw new PersistenceException();
        }
    }
}
