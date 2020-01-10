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
        sut.checkIfPlayerHasEnoughSaldo(TEST_AMOUNT, user);
        Mockito.verify(inventoryDAO, Mockito.times(1)).checkSaldo(TEST_AMOUNT, user);
    }

    @Test
    void checkSaldoReturnsRightBoolean() {
        Assertions.assertTrue(sut.checkIfPlayerHasEnoughSaldo(TEST_AMOUNT, user));
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
    void getInventoryCallsGetInventory() {
        sut.getInventory(user);
        Mockito.verify(inventoryDAO, Mockito.times(1)).getInventory(user);
    }

    @Test
    void checkWaterCallsCheckWater() {
        sut.checkIfPlayerHasEnoughWater(TEST_AMOUNT, user);
        Mockito.verify(inventoryDAO, Mockito.times(1)).checkWater(TEST_AMOUNT, user);
    }

    @Test
    void checkWaterReturnsFalseBoolean() {
        Assertions.assertFalse(sut.checkIfPlayerHasEnoughWater(TEST_AMOUNT, user));
    }

    @Test
    void lowerWaterCallsLowerWater() {
        sut.lowerWater(TEST_AMOUNT, user);
        Mockito.verify(inventoryDAO, Mockito.times(1)).lowerWater(TEST_AMOUNT, user);
    }

    @Test
    void increaseWaterCallsIncreaseWater() {
        sut.increaseWater(TEST_AMOUNT, user);
        Mockito.verify(inventoryDAO, Mockito.times(1)).increaseWater(TEST_AMOUNT, user);
    }

    private InventoryServiceTest() {
        sut.setInventoryDAO(inventoryDAO);
        Mockito.when(inventoryDAO.checkSaldo(TEST_AMOUNT, user)).thenReturn(true);
    }
}
