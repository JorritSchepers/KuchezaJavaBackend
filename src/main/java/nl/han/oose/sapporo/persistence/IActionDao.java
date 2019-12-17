package nl.han.oose.sapporo.persistence;

public interface IActionDao {
    void insertAction(int id, int actionID, String affectedName, int water, int money);
}
