package nl.han.oose.sapporo.resource;

import nl.han.oose.sapporo.dto.UserDTO;
import nl.han.oose.sapporo.service.IAccountService;
import nl.han.oose.sapporo.service.IInventoryService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class InventoryResourceTest {
    private InventoryResource sut = new InventoryResource();
    private IAccountService accountService;
    private IInventoryService inventoryService;
    private final String TOKEN = "123456789";
    private UserDTO user = new UserDTO();

    private InventoryResourceTest() {
        accountService = Mockito.mock(IAccountService.class);
        inventoryService = Mockito.mock(IInventoryService.class);
        Mockito.when(accountService.verifyToken(TOKEN)).thenReturn(user);
        sut.setAccountService(accountService);
        sut.setInventoryService(inventoryService);
    }

    @Test
    void getInventoryCallsVerifyToken() {
        sut.getInventory(TOKEN);
        Mockito.verify(accountService, Mockito.times(1)).verifyToken(TOKEN);
    }

    @Test
    void getInventoryCallsGetInventory() {
        sut.getInventory(TOKEN);
        Mockito.verify(inventoryService, Mockito.times(1)).getInventory(user);
    }
}
