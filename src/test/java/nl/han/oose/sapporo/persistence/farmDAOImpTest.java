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
    private IPlotDAO plotDAO;
    private UserDTO userWithFarm = new UserDTO(1, "PatrickSt3r", "DC00C903852BB19EB250AEBA05E534A6D211629D77D055033806B783BAE09937", "Patrick@Ster.com");
    private UserDTO userWithoutFarm = new UserDTO(2, "Sapporo", "DC00C903852BB19EB250AEBA05E534A6D211629D77D055033806B783BAE09937", "Patrick@Ster.com");
    private FarmDTO farm = new FarmDTO(2,2);

    @Override
    void setFactory(ConnectionFactoryImp connectionFactoryImp) {
        sut.setConnectionFactory(connectionFactoryImp);
    }

    @Test
    void hasFarmDoesNotThrowExceptionWhenUserHasNoFarm() {
        assertDoesNotThrow(() -> {
            sut.checkIfUserHasAFarm(userWithoutFarm);
        });
    }

    @Test
    void hasFarmThrowsExceptionWhenUserHasFarm() {
        assertThrows(UserAlreadyHasFarmException.class, () -> {
            sut.checkIfUserHasAFarm(userWithFarm);
        });
    }

    @Test
    void getFarmReturnsFarmFromUser(){
        FarmDTO expectedFarm = new FarmDTO(1,1);
        FarmDTO receivedFarm = sut.getFarm(userWithFarm);
        Assertions.assertEquals(expectedFarm,receivedFarm);
    }

    @Test
    void createFarmMakesExtraFarm(){
        int oldAmount = getAmountOfFarms();
        sut.createFarm(farm,userWithoutFarm);
        int newAmount = getAmountOfFarms();
        Assertions.assertEquals(oldAmount+1,newAmount);
    }


    private farmDAOImpTest(){
        plotDAO = Mockito.mock(IPlotDAO.class);
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
