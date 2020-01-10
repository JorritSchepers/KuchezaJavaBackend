package nl.han.oose.sapporo.service;
import nl.han.oose.sapporo.dto.AllActionsDTO;
import nl.han.oose.sapporo.dto.InventoryDTO;
import nl.han.oose.sapporo.dto.UserDTO;
import nl.han.oose.sapporo.persistence.IActionDao;

import javax.enterprise.inject.Default;
import javax.inject.Inject;

@Default
public class ActionService implements IActionService {
    private IActionDao actionDao;
    private IInventoryService inventoryService;

    @Inject
    public void setActionDao(IActionDao actionDao) {
        this.actionDao = actionDao;
    }

    @Inject
    public void setInventoryService(IInventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @Override
    public void setAction(UserDTO user, int actionID, String affectedName){
        InventoryDTO inventory = inventoryService.getInventory(user);
        actionDao.insertAction(user.getId(),actionID,affectedName,inventory.getWater(),inventory.getMoney());
    }

    @Override
    public AllActionsDTO getUserActions(int userID) {
        return actionDao.getUserActions(userID);
    }
}