package nl.han.oose.sapporo.persistence;

import nl.han.oose.sapporo.dto.FarmDTO;
import nl.han.oose.sapporo.dto.PlotDTO;
import nl.han.oose.sapporo.dto.UserDTO;
import nl.han.oose.sapporo.persistence.datasource.ConnectionFactoryImp;
import nl.han.oose.sapporo.persistence.exception.PersistenceException;
import nl.han.oose.sapporo.persistence.exception.UserAlreadyHasFarmException;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FarmDAOImp implements IFarmDAO {
    private ConnectionFactoryImp connectionFactory;
    private IPlotDAO plotDAO;

    @Inject
    public void setPlotDAO(IPlotDAO plotDAO) {
        this.plotDAO = plotDAO;
    }

    @Inject
    public void setConnectionFactoryImp(ConnectionFactoryImp connectionFactory){
        this.connectionFactory = connectionFactory;
    }

    @Override
    public void checkIfUserHasAFarm(UserDTO userDTO) {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM farm WHERE ownerID = ?");
            statement.setInt(1, userDTO.getId());
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()) {
                throw new UserAlreadyHasFarmException();
            }
        } catch (SQLException e) {
            throw new PersistenceException();
        }
    }

    @Override
    public FarmDTO createFarm(FarmDTO farmDTO, UserDTO userDTO){
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO farm (ownerID) VALUES (?)");
            statement.setInt(1, userDTO.getId());
            statement.execute();

            getFarmID(connection,farmDTO,userDTO);
            plotDAO.insertPlots(farmDTO);
            farmDTO.setPlots(getAllPlotsFromFarm(connection, farmDTO));
        } catch (SQLException e) {
            throw new PersistenceException();
        }

        return farmDTO;
    }

    private void getFarmID(Connection connection, FarmDTO farmDTO, UserDTO userDTO) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT farmID FROM farm WHERE ownerID = ?");
            statement.setInt(1, userDTO.getId());
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()) {
                farmDTO.setFarmID(resultSet.getInt("farmID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new PersistenceException();
        }
    }

    private List<PlotDTO> getAllPlotsFromFarm(Connection connection, FarmDTO farmDTO) {
        List<PlotDTO> plotDTOS = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM plot WHERE farmID = ?");
            statement.setInt(1, farmDTO.getFarmID());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                plotDTOS.add(new PlotDTO(
                        resultSet.getInt("plotID"),
                        resultSet.getInt("x"),
                        resultSet.getInt("y"),
                        resultSet.getInt("price"),
                        resultSet.getBoolean("purchased")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new PersistenceException();
        }
        return plotDTOS;
    }
}
