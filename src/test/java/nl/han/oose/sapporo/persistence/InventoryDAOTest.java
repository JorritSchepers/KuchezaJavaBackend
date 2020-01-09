package nl.han.oose.sapporo.persistence;

import nl.han.oose.sapporo.dto.InventoryDTO;
import nl.han.oose.sapporo.dto.UserDTO;
import nl.han.oose.sapporo.persistence.datasource.ConnectionFactoryImp;
import nl.han.oose.sapporo.persistence.exception.InsufficientFundsException;
import nl.han.oose.sapporo.persistence.exception.InsufficientWaterException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.*;

class InventoryDAOTest extends DAOTest {
    private InventoryDAOImp sut = new InventoryDAOImp();
    private final UserDTO USER = new UserDTO(1, "PatrickSt3r", "DC00C903852BB19EB250AEBA05E534A6D211629D77D055033806B783BAE09937", "Patrick@Ster.com");

    @Override
    void setFactory(ConnectionFactoryImp connectionFactoryImp) {
        sut.setConnectionFactory(connectionFactoryImp);
    }

    @Test
    void checkSaldoReturnsTrueWhenSaldoIsEnough() {
        int availableAmount = 10;
        Assertions.assertTrue(sut.checkSaldo(availableAmount, USER));
    }

    @Test
    void checkSaldoThrowsExceptionWhenSaldoIsNotEnough() {
        int unavailableAmount = 10000;
        Assertions.assertThrows(InsufficientFundsException.class, () -> {
            sut.checkSaldo(unavailableAmount, USER);
        });
    }

    @Test
    void lowerSaldoLowersSaldoWithRightAmount() {
        float removedSaldo = 10;
        float oldSaldo = getSaldoFromUser(USER.getId());
        sut.lowerSaldo(removedSaldo, USER);
        float newSaldo = getSaldoFromUser(USER.getId());
        Assertions.assertEquals((oldSaldo - removedSaldo), newSaldo);
    }

    @Test
    void increaseSaldoIncreasesSaldoWithRightAmount() {
        float extraSaldo = 10;
        float oldSaldo = getSaldoFromUser(USER.getId());
        sut.increaseSaldo(extraSaldo, USER);
        float newSaldo = getSaldoFromUser(USER.getId());
        Assertions.assertEquals((oldSaldo + extraSaldo), newSaldo);
    }

    @Test
    void checkWaterReturnsTrueWhenWaterIsEnough() {
        int availableAmount = 10;
        Assertions.assertTrue(sut.checkWater(availableAmount, USER));
    }

    @Test
    void checkWaterThrowsExceptionWhenWaterIsNotEnough() {
        int unavailableAmount = 10000;
        Assertions.assertThrows(InsufficientWaterException.class, () -> {
            sut.checkWater(unavailableAmount, USER);
        });
    }

    @Test
    void lowerWaterLowersWaterWithRightAmount() {
        int removedWater = 10;
        int oldWater = getWaterFromUser(USER.getId());
        sut.lowerWater(removedWater, USER);
        int newWater = getWaterFromUser(USER.getId());
        Assertions.assertEquals((oldWater - removedWater), newWater);
    }

    @Test
    void increaseWaterIncreasesWaterWithRightAmount() {
        int extraWater = 10;
        int oldWater = getWaterFromUser(USER.getId());
        sut.increaseWater(extraWater, USER);
        int newWater = getWaterFromUser(USER.getId());
        Assertions.assertEquals((oldWater + extraWater), newWater);
    }

    @Test
    void getInventoryReturnsRightInventory() {
        InventoryDTO expectedInventoryDTO = new InventoryDTO(1, 2000, 1000);
        Assertions.assertEquals(expectedInventoryDTO, sut.getInventory(USER));
    }

    @Test
    void createInventoryMakesInventoryWithRightAmounts() {
        final int USERID = 4;
        final int STARTMONEY = 10000;
        final int STARTWATER = 20000;
        UserDTO user = new UserDTO(USERID, "name", "password", "email");
        sut.createInventory(user);
        Assertions.assertEquals(getSaldoFromUser(USERID), STARTMONEY);
        Assertions.assertEquals(getWaterFromUser(USERID), STARTWATER);
    }

    private int getSaldoFromUser(int userId) {
        int saldo = 0;
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            PreparedStatement statement = connection.prepareStatement("SELECT money FROM inventory where userID =?");
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                saldo = resultSet.getInt("money");
            }
        } catch (SQLException ignored) {
        }
        return saldo;
    }

    private int getWaterFromUser(int userId) {
        int water = 0;
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            PreparedStatement statement = connection.prepareStatement("SELECT water FROM inventory where userID =?");
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                water = resultSet.getInt("water");
            }
        } catch (SQLException ignored) {
        }
        return water;
    }
}
