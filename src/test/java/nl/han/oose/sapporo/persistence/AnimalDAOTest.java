package nl.han.oose.sapporo.persistence;

import nl.han.oose.sapporo.dto.AllAnimalDTO;
import nl.han.oose.sapporo.dto.AnimalDTO;
import nl.han.oose.sapporo.persistence.datasource.ConnectionFactoryImp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.*;
import java.util.ArrayList;

class AnimalDAOTest extends DAOTest {
    private AnimalDAOImp sut = new AnimalDAOImp();

    @Override
    void setFactory(ConnectionFactoryImp connectionFactoryImp) {
        sut.setConnectionFactory(connectionFactoryImp);
    }


    private Boolean DoesAnimalExist(int id){
        try (Connection connection = DriverManager.getConnection(DB_URL)) {
            PreparedStatement statement = connection.prepareStatement("select * from animal where animalId = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            return (resultSet.next());
        } catch (SQLException ignored) {
        }
        return false;
    }

    @Test
    void getAllAnimalsGetsRightAmountOfAnimals() {
        int AMOUNT_OF_ANIMALS = 2;
        AllAnimalDTO allAnimals = sut.getAllAnimals();
        ArrayList<AnimalDTO> animals = allAnimals.getAnimals();
        Assertions.assertEquals(AMOUNT_OF_ANIMALS, animals.size());
    }

    @Test
    void deleteAnimalDeletesAnimal(){
        final int DELETEID = 1;
        Assertions.assertTrue(DoesAnimalExist(DELETEID));
        sut.deleteAnimal(DELETEID);
        Assertions.assertFalse(DoesAnimalExist(DELETEID));
    }
}
