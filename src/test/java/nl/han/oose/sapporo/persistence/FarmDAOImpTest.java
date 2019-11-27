package nl.han.oose.sapporo.persistence;

import nl.han.oose.sapporo.dto.FarmDTO;
import nl.han.oose.sapporo.dto.PlotDTO;
import nl.han.oose.sapporo.dto.UserDTO;
import nl.han.oose.sapporo.persistence.datasource.ConnectionFactoryImp;
import nl.han.oose.sapporo.persistence.exception.UserAlreadyHasFarmException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FarmDAOImpTest extends DAOTest {
    private FarmDAOImp sut = new FarmDAOImp();
    private IPlotDAO plotDAO;
    private UserDTO userWithFarm = new UserDTO(1, "PatrickSt3r", "DC00C903852BB19EB250AEBA05E534A6D211629D77D055033806B783BAE09937", "Patrick@Ster.com");
    private UserDTO userWithoutFarm = new UserDTO(2, "Sapporo", "DC00C903852BB19EB250AEBA05E534A6D211629D77D055033806B783BAE09937", "Patrick@Ster.com");
    private Connection connection;
    private FarmDTO farmDTO;
    private FarmDTO farm = new FarmDTO(2,2);
    private List<PlotDTO> plotDTOS;

    @Override
    void setfactory(ConnectionFactoryImp connectionFactoryImp) {
        sut.setConnectionFactory(connectionFactoryImp);
    }

    private int getAmountOfFarms() {
        int farms = 0;
        try (Connection connection = DriverManager.getConnection(dbUrl)) {
            PreparedStatement statement = connection.prepareStatement("select count(farmID) as amount from farm");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                farms = resultSet.getInt("amount");
            }
        } catch (SQLException ignored) {
        }
        return farms;
    }

    FarmDAOImpTest(){
        plotDAO = Mockito.mock(IPlotDAO.class);
        sut.setPlotDAO(plotDAO);
    }

    @Test
    void HasFarmDoesNotThrowExceptionWhenUserHasNoFarm() {
        assertDoesNotThrow(() -> {
            sut.checkIfUserHasAFarm(userWithoutFarm);
        });
    }

    @Test
    void HasFarmThrowsExceptionWhenUserHasFarm() {
        assertThrows(UserAlreadyHasFarmException.class, () -> {
            sut.checkIfUserHasAFarm(userWithFarm);
        });
    }

    @Test
    void getFarmReturnsFarmFromUser(){
        FarmDTO expectedFarm = new FarmDTO(1,1);
        FarmDTO gottenFarm = sut.getFarm(userWithFarm);
        Assertions.assertEquals(expectedFarm,gottenFarm);
    }

    @Test
    void createFarmMakesExtraFarm(){
        int oldamount = getAmountOfFarms();
        sut.createFarm(farm,userWithoutFarm);
        int newamount = getAmountOfFarms();
        Assertions.assertEquals(oldamount+1,newamount);
    }

}