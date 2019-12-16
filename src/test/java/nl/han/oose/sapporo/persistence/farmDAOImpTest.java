package nl.han.oose.sapporo.persistence;

import nl.han.oose.sapporo.dto.FarmDTO;
import nl.han.oose.sapporo.dto.UserDTO;
import nl.han.oose.sapporo.persistence.datasource.ConnectionFactoryImp;
import nl.han.oose.sapporo.persistence.exception.UserAlreadyHasFarmException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class farmDAOImpTest extends DAOTest {
    private FarmDAOImp sut = new FarmDAOImp();
    private final UserDTO USER_WITH_FARM = new UserDTO(1, "PatrickSt3r", "DC00C903852BB19EB250AEBA05E534A6D211629D77D055033806B783BAE09937", "Patrick@Ster.com");
    private final UserDTO USER_WITHOUT_FARM = new UserDTO(2, "Sapporo", "DC00C903852BB19EB250AEBA05E534A6D211629D77D055033806B783BAE09937", "Patrick@Ster.com");
    private final FarmDTO FARM = new FarmDTO(2,2);

    @Override
    void setFactory(ConnectionFactoryImp connectionFactoryImp) {
        sut.setConnectionFactory(connectionFactoryImp);
    }

    @Test
    void hasFarmDoesNotThrowExceptionWhenUserHasNoFarm() {
        assertDoesNotThrow(() -> {
            sut.checkIfUserHasAFarm(USER_WITHOUT_FARM);
        });
    }

    @Test
    void hasFarmThrowsExceptionWhenUserHasFarm() {
        assertThrows(UserAlreadyHasFarmException.class, () -> {
            sut.checkIfUserHasAFarm(USER_WITH_FARM);
        });
    }

    @Test
    void getFarmReturnsFarmFromUser(){
        FarmDTO expectedFarm = new FarmDTO(1,1);
        FarmDTO receivedFarm = sut.getFarm(USER_WITH_FARM);
        Assertions.assertEquals(expectedFarm,receivedFarm);
    }

    @Test
    void createFarmMakesExtraFarm(){
        int oldAmount = getAmountOfFarms();
        sut.createFarm(FARM, USER_WITHOUT_FARM);
        int newAmount = getAmountOfFarms();
        Assertions.assertEquals(oldAmount+1,newAmount);
    }


    private farmDAOImpTest(){
        IPlotDAO plotDAO = Mockito.mock(IPlotDAO.class);
        sut.setPlotDAO(plotDAO);
    }

    private int getAmountOfFarms() {
        int totalFarms = 0;
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            PreparedStatement statement = connection.prepareStatement("select count(farmID) as amount from farm");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                totalFarms = resultSet.getInt("amount");
            }
        } catch (SQLException ignored) {
        }
        return totalFarms;
    }
}
