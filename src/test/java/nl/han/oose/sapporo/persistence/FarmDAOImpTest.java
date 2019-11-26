package nl.han.oose.sapporo.persistence;

import nl.han.oose.sapporo.dto.FarmDTO;
import nl.han.oose.sapporo.dto.PlotDTO;
import nl.han.oose.sapporo.dto.UserDTO;
import nl.han.oose.sapporo.persistence.datasource.ConnectionFactoryImp;
import nl.han.oose.sapporo.persistence.exception.UserAlreadyHasFarmException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FarmDAOImpTest extends DAOTest {
    private FarmDAOImp sut = new FarmDAOImp();
    private UserDTO userWithFarm = new UserDTO(1,"PatrickSt3r","DC00C903852BB19EB250AEBA05E534A6D211629D77D055033806B783BAE09937","Patrick@Ster.com");
    private UserDTO userWithoutFarm = new UserDTO(2,"Sapporo","DC00C903852BB19EB250AEBA05E534A6D211629D77D055033806B783BAE09937","Patrick@Ster.com");
    private FarmDTO farmDTO;
    private List<PlotDTO> plotDTOS;

    @Override
    void setfactory(ConnectionFactoryImp connectionFactoryImp) {
        sut.setConnectionFactory(connectionFactoryImp);
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
    void createFarmAddsFarmToDatabase() {
        sut.createFarm(farmDTO,userWithoutFarm);
    }
}