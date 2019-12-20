package nl.han.oose.sapporo.persistence;

import nl.han.oose.sapporo.dto.ActionDTO;
import nl.han.oose.sapporo.dto.AllActionsDTO;
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

    ActionDTO getActionWithID_AND_ACTION_ID(int userID, int ActionID, int water){
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
        final int ID = 1;
        final int MONEY = 100;
        final int WATER = 100;
        final String AFFECTED_ITEM = "Corn";
        final int ACTION_ID = 1;

        ActionDTO expectedAction = new ActionDTO(ID, ACTION_ID,DATE, AFFECTED_ITEM, WATER, MONEY);
        sut.insertAction(ID, ACTION_ID, AFFECTED_ITEM, WATER, MONEY);
        ActionDTO actualAction = getActionWithID_AND_ACTION_ID(ID, ACTION_ID, WATER);
        Assertions.assertEquals(actualAction,expectedAction);
    }

    @Test
    void getUserActionsGetsRightAmount(){
        final int USERID =1;
        final int RIGHTAMOUNT =3;
        AllActionsDTO Allactions = sut.getUserActions(USERID);
        int actualAmount = Allactions.getActions().size();
        Assertions.assertEquals(RIGHTAMOUNT,actualAmount);
    }
}
