package nl.han.oose.sapporo.service;

import nl.han.oose.sapporo.dto.UserDTO;
import nl.han.oose.sapporo.persistence.IInventoryDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class InventoryServiceTest {
    private InventoryServiceImp sut = new InventoryServiceImp();
    private IInventoryDAO inventoryDAO = Mockito.mock(IInventoryDAO.class);
    private final int TEST_AMOUNT = 10;
    private UserDTO user = new UserDTO();

    @Test
    void checkSaldoCallsCheckSalo() {
        sut.checkSaldo(TEST_AMOUNT, user);
        Mockito.verify(inventoryDAO, Mockito.times(1)).checkSaldo(TEST_AMOUNT, user);
    }

    @Test
    void checkSaldoReturnsRightBoolean() {
        Assertions.assertTrue(sut.checkSaldo(TEST_AMOUNT, user));
    }

    @Test
    void lowerSaldoCallsLowerSaldo() {
        sut.lowerSaldo(TEST_AMOUNT, user);
        Mockito.verify(inventoryDAO, Mockito.times(1)).lowerSaldo(TEST_AMOUNT, user);
    }

    @Test
    void increaseSaldoCallsIncreaseSaldo() {
        sut.increaseSaldo(TEST_AMOUNT, user);
        Mockito.verify(inventoryDAO, Mockito.times(1)).increaseSaldo(TEST_AMOUNT, user);
    }

    @Test
    void getInventoryCallsGetInventory(){
        sut.getInventory(user);
        Mockito.verify(inventoryDAO, Mockito.times(1)).getInventory(user);
    }

    private InventoryServiceTest() {
        sut.setInventoryDAO(inventoryDAO);
        Mockito.when(inventoryDAO.checkSaldo(TEST_AMOUNT, user)).thenReturn(true);
    }
}
