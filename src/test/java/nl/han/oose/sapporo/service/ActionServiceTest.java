package nl.han.oose.sapporo.service;

import nl.han.oose.sapporo.dto.InventoryDTO;
import nl.han.oose.sapporo.dto.UserDTO;
import nl.han.oose.sapporo.persistence.IActionDao;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ActionServiceTest {
    private ActionService sut = new ActionService();
    private IActionDao actionDao = Mockito.mock(IActionDao.class);
    private IInventoryService inventoryService = Mockito.mock(IInventoryService.class);
    private final int USER_ID = 1;
    private final String AFFECTED_NAME = "Affected";
    private final String USERNAME = "name";
    private final String PASSWORD = "Password";
    private final String EMAIL = "email";
    private final boolean ADMIN = false;
    private final int ACTION_ID = 1;

    private UserDTO user;

    ActionServiceTest(){
        user = new UserDTO(USER_ID,USERNAME,PASSWORD,EMAIL,ADMIN);
        sut.setActionDao(actionDao);
        sut.setInventoryService(inventoryService);
    }

    @Test
    void setActionCallsGetInventory(){
        Mockito.when(inventoryService.getInventory(user)).thenReturn(new InventoryDTO(USER_ID,100,100));
        sut.setAction(user, ACTION_ID, AFFECTED_NAME);
        Mockito.verify(inventoryService, Mockito.times(1)).getInventory(user);
    }

    @Test
    void setActionCallsInsertAction(){
        final int WATER = 100;
        final int MONEY = 300;
        Mockito.when(inventoryService.getInventory(user)).thenReturn(new InventoryDTO(USER_ID,MONEY,WATER));
        sut.setAction(user, ACTION_ID, AFFECTED_NAME);
        Mockito.verify(actionDao, Mockito.times(1)).insertAction(USER_ID, ACTION_ID, AFFECTED_NAME,WATER,MONEY);
    }

    @Test
    void getUserActionsCallsGetUserActions(){
        sut.getUserActions(USER_ID );
        Mockito.verify(actionDao, Mockito.times(1)).getUserActions(USER_ID );
    }
}
