package nl.han.oose.sapporo.persistence;

import nl.han.oose.sapporo.dto.ActionDTO;
import nl.han.oose.sapporo.persistence.datasource.ConnectionFactoryImp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.*;

class ActionDaoTest extends DAOTest {
    private ActionDao sut =  new ActionDao();
    private final Timestamp DATE = null;

    @Override
    void setFactory(ConnectionFactoryImp connectionFactoryImp) {
        sut.setConnectionFactory(connectionFactoryImp);
    }

    ActionDTO getActionWithIDANDACTIONID(int userID,int ActionID,int water){
        ActionDTO action = null;
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            PreparedStatement statement = connection.prepareStatement("select * from actionPerPlayer where userID = ? and actionID = ? and currentWater = ?");
            statement.setInt(1, userID);
            statement.setInt(2, ActionID);
            statement.setInt(3, water);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int user = resultSet.getInt("userID");
                int actionID = resultSet.getInt("actionID");
                Timestamp dateOfAction = resultSet.getTimestamp("dateOfAction");
                String affectedItem = resultSet.getString("affectedItem");
                int currentWater = resultSet.getInt("currentWater");
                int currentMoney = resultSet.getInt("currentMoney");
                action = new ActionDTO(user,actionID,dateOfAction,affectedItem,currentWater,currentMoney);
            }
        } catch (SQLException ignored) {
        }
        return action;
    }

    @Test
    void insertActionInsertsAction(){
        int ID = 1;
        int MONEY = 100;
        int WATER = 100;
        String AFFECTEDITEM = "Corn";
        int ACTIONID = 1;

        ActionDTO expectedAction = new ActionDTO(ID, ACTIONID,DATE, AFFECTEDITEM, WATER, MONEY);
        sut.insertAction(ID, ACTIONID, AFFECTEDITEM, WATER, MONEY);
        ActionDTO actualAction = getActionWithIDANDACTIONID(ID, ACTIONID, WATER);
        Assertions.assertEquals(actualAction,expectedAction);
    }
}
