package nl.han.oose.sapporo.persistence;

import nl.han.oose.sapporo.dto.UserDTO;
import nl.han.oose.sapporo.persistence.datasource.ConnectionFactoryImp;
import nl.han.oose.sapporo.persistence.exception.InsufficientFundsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.*;

class InventoryDAOTest extends DAOTest {
    private InventoryDAOImp sut = new InventoryDAOImp();
    private UserDTO userDTO = new UserDTO("PatrickSt3r","DC00C903852BB19EB250AEBA05E534A6D211629D77D055033806B783BAE09937","Patrick@Ster.com",1);

    @Override
    void setfactory(ConnectionFactoryImp connectionFactoryImp) {
        sut.setConnectionFactory(connectionFactoryImp);
    }

    private int getSaldoFromUser(int userId) {
        int saldo = 0;
        try (Connection connection = DriverManager.getConnection(dbUrl)) {
            PreparedStatement statement = connection.prepareStatement("SELECT money FROM inventory where userID =?");
            statement.setInt(1,userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                saldo = resultSet.getInt("money");
            }
        } catch (SQLException ignored) {
        }
        return saldo;
    }

    @Test
    void checkSaldoReturnsTrueWhenSaldoIsEnough(){
        int availableAmount = 10;
        Assertions.assertTrue(sut.checkSaldo(availableAmount,userDTO));
    }

    @Test
    void checkSaldoThrowsExceptionWhenSaldoIsNotEnough(){
        int unavailableAmount = 10000;
        Assertions.assertThrows(InsufficientFundsException.class, () -> {
            sut.checkSaldo(unavailableAmount,userDTO);
        });
    }

    @Test
    void lowerSaldoLowersSaldoWithRightAmount(){
        float removedSaldo = 10;
        float oldSaldo = getSaldoFromUser(userDTO.getId());
        sut.lowerSaldo(removedSaldo, userDTO);
        float newSaldo = getSaldoFromUser(userDTO.getId());
        Assertions.assertEquals((oldSaldo-removedSaldo),newSaldo);
    }
}