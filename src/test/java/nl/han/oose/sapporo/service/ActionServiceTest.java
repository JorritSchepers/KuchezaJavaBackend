package nl.han.oose.sapporo.service;

import nl.han.oose.sapporo.dto.InventoryDTO;
import nl.han.oose.sapporo.dto.UserDTO;
import nl.han.oose.sapporo.persistence.IActionDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ActionServiceTest {
    private ActionService sut = new ActionService();
    private IActionDao actionDao = Mockito.mock(IActionDao.class);
    private IInventoryService inventoryService = Mockito.mock(IInventoryService.class);
    private final int USERID = 1;
    private final String AFFECTEDNAME = "Affected";
    private final String USERNAME = "name";
    private final String PASSWORD = "Password";
    private final String EMAIL = "email";
    private final boolean ADMIN = false;
    private final int ACTIONID = 1;

    private UserDTO user;

    ActionServiceTest(){
        user = new UserDTO(USERID,USERNAME,PASSWORD,EMAIL,ADMIN);
        sut.setActionDao(actionDao);
        sut.setInventoryService(inventoryService);
    }

    @Test
    void setActionCallsGetInventory(){
        Mockito.when(inventoryService.getInventory(user)).thenReturn(new InventoryDTO(USERID,100,100));
        sut.setAction(user,ACTIONID,AFFECTEDNAME);
        Mockito.verify(inventoryService, Mockito.times(1)).getInventory(user);
    }

    @Test
    void setActionCallsInsertAction(){
        final int WATER = 100;
        final int MONEY = 300;
        Mockito.when(inventoryService.getInventory(user)).thenReturn(new InventoryDTO(USERID,MONEY,WATER));
        sut.setAction(user,ACTIONID,AFFECTEDNAME);
        Mockito.verify(actionDao, Mockito.times(1)).insertAction(USERID,ACTIONID,AFFECTEDNAME,WATER,MONEY);
    }
}
