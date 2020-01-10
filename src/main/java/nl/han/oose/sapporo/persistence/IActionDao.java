package nl.han.oose.sapporo.persistence;

import nl.han.oose.sapporo.dto.AllActionsDTO;

public interface IActionDao {
    void insertAction(int id, int actionID, String affectedName, int water, int money);

    AllActionsDTO getUserActions(int userID);
}
