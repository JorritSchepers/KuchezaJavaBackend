package nl.han.oose.sapporo.persistence;

import nl.han.oose.sapporo.dto.FarmDTO;
import nl.han.oose.sapporo.dto.PlotDTO;
import nl.han.oose.sapporo.dto.UserDTO;
import nl.han.oose.sapporo.persistence.datasource.ConnectionFactoryImp;
import nl.han.oose.sapporo.persistence.exception.PersistenceException;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.stream.Collectors;

public class FarmDAOImp implements IFarmDAO {
    private ConnectionFactoryImp connectionFactory;

    @Inject
    public void setConnectionFactoryImp(ConnectionFactoryImp connectionFactory){
        this.connectionFactory = connectionFactory;
    }

    @Override
    public FarmDTO addFarm(FarmDTO farmDTO, UserDTO userDTO){
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO farm (ownerID) VALUES (?)");
            statement.setInt(1, userDTO.getId());
            statement.execute();

            statement = connection.prepareStatement("SELECT farmID FROM farm WHERE ownerID = ?");
            statement.setInt(1, userDTO.getId());
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()) {
                farmDTO.setFarmID(resultSet.getInt("farmID"));
            }

            for(PlotDTO plot: farmDTO.getPlots()) {
                statement = connection.prepareStatement("INSERT INTO plot (x,y,price,purchased,farmID) VALUES(?,?,?,?,?);");
                statement.setInt(1,plot.getX());
                statement.setInt(2,plot.getY());
                statement.setFloat(3,plot.getPrice());
                statement.setBoolean(4,plot.isPurchased());
                statement.setInt(5,farmDTO.getFarmID());
                statement.execute();
            }

            statement = connection.prepareStatement("SELECT * FROM plot WHERE farmID = ?");
            statement.setInt(1, farmDTO.getFarmID());
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int plotID = resultSet.getInt("plotID");
                int x = resultSet.getInt("x");
                int y = resultSet.getInt("y");
                PlotDTO correctPlot = farmDTO.getPlots().stream().filter(plotDTO -> {
                    if(plotDTO.getX() == x && plotDTO.getY() == y) {
                        return true;
                    } else {
                        return false;
                    }
                }).collect(Collectors.toList()).get(0);
                correctPlot.setID(plotID);
            }

        } catch (SQLException e) {
            throw new PersistenceException();
        }

        return farmDTO;
    }
}
