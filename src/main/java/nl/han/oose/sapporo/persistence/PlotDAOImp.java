package nl.han.oose.sapporo.persistence;

import nl.han.oose.sapporo.dto.AnimalDTO;
import nl.han.oose.sapporo.dto.FarmDTO;
import nl.han.oose.sapporo.dto.PlantDTO;
import nl.han.oose.sapporo.dto.PlotDTO;
import nl.han.oose.sapporo.persistence.datasource.ConnectionFactoryImp;
import nl.han.oose.sapporo.persistence.exception.*;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PlotDAOImp implements IPlotDAO {
    private ConnectionFactoryImp connectionFactory;

    @Inject
    public void setConnectionFactory(ConnectionFactoryImp connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public void addPlantToPlot(PlantDTO plantDTO, int plotID) {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("update plot set plantID = ?, waterAvailable = ? where plotID = ? ");
            statement.setInt(1, plantDTO.getId());
            statement.setInt(2, plantDTO.getMaximumWater()/4);
            statement.setInt(3, plotID);
            statement.execute();
        } catch (SQLException e) {
            throw new PersistenceException();
        }
    }

    @Override
    public boolean checkIfPlotIsEmpty(int plotID) {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("select animalID,watermanagerID,plantID,waterSourceID from plot where plotID = ?");
            statement.setInt(1, plotID);
            ResultSet resultSet = statement.executeQuery();
            int plotTaken = 0;
            int animalID = 0;
            int waterManagerID = 0;
            int plantID = 0;
            int waterSourceID = 0;

            while (resultSet.next()) {
                animalID = resultSet.getInt("animalID");
                waterManagerID = resultSet.getInt("watermanagerID");
                plantID = resultSet.getInt("plantID");
                waterSourceID = resultSet.getInt("waterSourceID");
            }

            plotTaken += animalID + waterManagerID + plantID + waterSourceID;

            if (plotTaken == 0) {
                return true;
            } else {
                throw new PlotIsOccupiedException();
            }
        } catch (SQLException e) {
            throw new PersistenceException();
        }
    }

    @Override
    public boolean checkIfPlotHasWater(int plotID) {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("select plantID,waterSourceID,animalID from plot where plotID = ?");
            statement.setInt(1, plotID);
            ResultSet resultSet = statement.executeQuery();
            int plotTaken = 0;
            int plantID = 0;
            int waterSourceID = 0;
            int animalID = 0;

            while (resultSet.next()) {
                plantID = resultSet.getInt("plantID");
                waterSourceID = resultSet.getInt("waterSourceID");
                animalID = resultSet.getInt("animalID");
            }

            plotTaken += plantID + waterSourceID + animalID;

            return plotTaken != 0;
        } catch (SQLException e) {
            throw new PersistenceException();
        }
    }

    @Override
    public boolean plotIsPurchased(int plotID) {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("select purchased from plot where plotID = ?");
            statement.setInt(1, plotID);
            ResultSet resultSet = statement.executeQuery();
            boolean plotPurchased = false;

            while (resultSet.next()) {
                plotPurchased = resultSet.getBoolean("purchased");
            }

            return plotPurchased;
        } catch (SQLException e) {
            throw new PersistenceException();
        }
    }

    @Override
    public PlotDTO getPlot(int plotID) {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("select * from plot where plotID = ?");
            statement.setInt(1, plotID);
            ResultSet resultSet = statement.executeQuery();
            PlotDTO plotDTO = null;

            while (resultSet.next()) {
                plotDTO = createNewPlot(resultSet);
            }

            if (plotDTO == null) {
                throw new PlotDoesNotExistException();
            } else {
                return plotDTO;
            }
        } catch (SQLException e) {
            throw new PersistenceException();
        }
    }

    private PlotDTO createNewPlot(ResultSet resultSet) throws SQLException {
        return new PlotDTO(
                resultSet.getInt("plotID"),
                resultSet.getInt("x"),
                resultSet.getInt("y"),
                resultSet.getInt("animalID"),
                resultSet.getInt("waterManagerID"),
                resultSet.getInt("plantID"),
                resultSet.getInt("waterSourceID"),
                resultSet.getFloat("price"),
                resultSet.getBoolean("purchased"),
                resultSet.getInt("objectAge"),
                resultSet.getInt("waterAvailable"),
                resultSet.getString("status")
        );
    }


    @Override
    public void removeObjectsFromPlot(int plotID) {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("update plot set animalId = null, waterManagerId = null, plantID = null, objectAge = 0, waterAvailable = 0, status='Normal' where plotID = ?");
            statement.setInt(1, plotID);
            statement.execute();
        } catch (SQLException e) {
            throw new PersistenceException();
        }
    }

    @Override
    public void purchasePlot(int plotID) {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("update plot set purchased = 1 where plotID = ?");
            statement.setInt(1, plotID);
            statement.execute();
        } catch (SQLException e) {
            throw new PersistenceException();
        }
    }

    @Override
    public boolean plotHasPlant(int plotID) {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("select plantID from plot where plotID = ?");
            statement.setInt(1, plotID);
            ResultSet resultSet = statement.executeQuery();

            int plantID = 0;

            while (resultSet.next()) {
                plantID = resultSet.getInt("plantID");
            }
            if (plantID == 0) {
                throw new PlotHasNoPlantException();
            } else return true;
        } catch (SQLException e) {
            throw new PersistenceException();
        }
    }

    @Override
    public boolean plotHasWaterResource(int plotID) {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("select waterSourceID from plot where plotID = ?");
            statement.setInt(1, plotID);
            ResultSet resultSet = statement.executeQuery();

            int waterResourceID = 0;

            while (resultSet.next()) {
                waterResourceID = resultSet.getInt("waterSourceID");
            }
            if (waterResourceID == 0) {
                throw new PlotHasNoWaterSourceException();
            } else return true;
        } catch (SQLException e) {
            throw new PersistenceException();
        }
    }

    public void insertPlots(FarmDTO farmDTO) {
        try (Connection connection = connectionFactory.getConnection()) {
            for (PlotDTO plot : farmDTO.getPlots()) {
                PreparedStatement statement = connection.prepareStatement("INSERT INTO plot (x,y,price,purchased,farmID) VALUES(?,?,?,?,?);");
                statement.setInt(1, plot.getX());
                statement.setInt(2, plot.getY());
                statement.setFloat(3, plot.getPrice());
                statement.setBoolean(4, plot.isPurchased());
                statement.setInt(5, farmDTO.getFarmID());
                statement.execute();
            }
        } catch (SQLException e) {
            throw new PersistenceException();
        }
    }

    @Override
    public ArrayList<PlotDTO> getFarmPlots(int farmID) {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM plot where farmID = ?");
            statement.setInt(1, farmID);
            ResultSet resultSet = statement.executeQuery();
            ArrayList<PlotDTO> plots = new ArrayList<>();

            while (resultSet.next()) {
                PlotDTO plot = createNewPlot(resultSet);
                plots.add(plot);
            }
            return plots;
        } catch (SQLException e) {
            throw new PersistenceException();
        }
    }

    @Override
    public void updateAge(int plotID, int age) {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE plot SET objectAge = ? WHERE plotID = ?");
            statement.setInt(1, age);
            statement.setInt(2, plotID);
            statement.execute();
        } catch (SQLException e) {
            throw new PersistenceException();
        }
    }

    @Override
    public void changeStatus(int plotID, String status) {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("UPDATE plot SET status = ? WHERE plotID = ?");
            statement.setString(1, status);
            statement.setInt(2, plotID);
            statement.execute();
        } catch (SQLException e) {
            throw new PersistenceException();
        }
    }

    public void editWaterAvailable(int amount, int plotID) {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement
                    ("update plot set waterAvailable = waterAvailable+? where plotID = ?");
            statement.setInt(1, amount);
            statement.setInt(2, plotID);
            statement.execute();
        } catch (SQLException e) {
            throw new WaterOutOfBoundsException();
        }
    }

    @Override
    public void createSilo(FarmDTO farmDTO) {
        final int SILO_ID = 1;
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement
                    ("update plot set waterManagerID = ? where farmID = ? order by plotID LIMIT 1 ");
            statement.setInt(1, SILO_ID);
            statement.setInt(2, farmDTO.getFarmID());
            statement.execute();
        } catch (SQLException e) {
            throw new PersistenceException();
        }
    }

    @Override
    public void createWell(FarmDTO farmDTO) {
        final int WELL_ID = 1;
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement
                    ("UPDATE plot SET waterSourceID = ? WHERE plotID IN (SELECT plotID FROM (select * from plot where farmID = ? order by plotID limit 1,1)l);\n" +
                            " ");
            statement.setInt(1, WELL_ID);
            statement.setInt(2, farmDTO.getFarmID());
            statement.execute();
        } catch (SQLException e) {
            throw new PersistenceException();
        }
    }

    @Override
    public String getAnimalFromPlot(int plotID) {
        String name = null;
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement
                    ("select name from plot inner join animal on animal.animalID = plot.animalID where plot.plotID = ?");
            statement.setInt(1, plotID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                name = resultSet.getString("name");
            }
        } catch (SQLException e) {
            throw new PersistenceException();
        }
        return name;
    }

    @Override
    public int getWater(int plotID) {
        int water = 0;
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("select waterAvailable from plot where plotID = ?");
            statement.setInt(1, plotID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                water = resultSet.getInt("waterAvailable");
            } else {
                throw new PlotDoesNotExistException();
            }
        } catch (SQLException e) {
            throw new PersistenceException();
        }
        return water;
    }

    @Override
    public void replacePlantsOnAllPlots(int plantIDToDelete, int plantIDToReplaceWith) {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("update plot set plantID = ? where plantID = ?");
            statement.setInt(1, plantIDToReplaceWith);
            statement.setInt(2, plantIDToDelete);
            statement.execute();
        } catch (SQLException e) {
            throw new PersistenceException();
        }
    }

    public void addAnimalToPlot(AnimalDTO animalDTO, int plotID) {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("update plot set animalID = ?, waterAvailable = ? where plotID = ? ");
            statement.setInt(1, animalDTO.getId());
            statement.setInt(2, animalDTO.getMaximumWater()/4);
            statement.setInt(3, plotID);
            statement.execute();
        } catch (SQLException e) {
            throw new PersistenceException();
        }
    }

    @Override
    public boolean plotHasAnimal(int plotID) {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("select animalID from plot where plotID = ?");
            statement.setInt(1, plotID);
            ResultSet resultSet = statement.executeQuery();

            int animalID = 0;

            while (resultSet.next()) {
                animalID = resultSet.getInt("animalID");
            }
            if (animalID == 0) {
                throw new PlotHasNoAnimalException();
            } else return true;
        } catch (SQLException e) {
            throw new PersistenceException();
        }
    }

    @Override
    public void replaceAnimalsOnAllPlots(int animalIDToDelete, int animalIDToReplaceWith) {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("update plot set animalID = ? where animalID = ?");
            statement.setInt(1, animalIDToReplaceWith);
            statement.setInt(2, animalIDToDelete);
            statement.execute();
        } catch (SQLException e) {
            throw new PersistenceException();
        }
    }
}
