package nl.han.oose.sapporo.service;

import nl.han.oose.sapporo.dto.UserDTO;
import nl.han.oose.sapporo.persistence.IInventoryDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class InventoryServiceTest {
    private InventoryServiceImp sut = new InventoryServiceImp();
    private IInventoryDAO inventoryDAO = Mockito.mock(IInventoryDAO.class);
    private final int TESTAMOUNT = 10;
    private UserDTO user = new UserDTO();

    InventoryServiceTest(){
      sut.setInventoryDAO(inventoryDAO);
      Mockito.when(inventoryDAO.checkSaldo(TESTAMOUNT,user)).thenReturn(true);
    }

    @Test
    void checkSaldoCallsCheckSalo(){
        sut.checkSaldo(TESTAMOUNT,user);
        Mockito.verify(inventoryDAO, Mockito.times(1)).checkSaldo(TESTAMOUNT,user);
    }

    @Test
    void checkSaldoReturnsRightBoolean(){
        Assertions.assertTrue(sut.checkSaldo(TESTAMOUNT,user));
    }

    @Test
    void lowerSaldoCallsLowerSalo(){
        sut.lowerSaldo(TESTAMOUNT,user);
        Mockito.verify(inventoryDAO, Mockito.times(1)).lowerSaldo(TESTAMOUNT,user);
    }

    @Test
    void increaseSaldoCallsIncreaseSaldo(){
        sut.increaseSaldo(TESTAMOUNT,user);
        Mockito.verify(inventoryDAO,Mockito.times(1)).increaseSaldo(TESTAMOUNT,user);
    }
}