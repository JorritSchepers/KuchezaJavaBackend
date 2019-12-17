package nl.han.oose.sapporo.persistence;

import nl.han.oose.sapporo.dto.AllAnimalDTO;
import nl.han.oose.sapporo.dto.AnimalDTO;
import nl.han.oose.sapporo.persistence.datasource.ConnectionFactoryImp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class AnimalDAOTest extends DAOTest {
    private AnimalDAOImp sut = new AnimalDAOImp();

    @Override
    void setFactory(ConnectionFactoryImp connectionFactoryImp) {
        sut.setConnectionFactory(connectionFactoryImp);
    }

    @Test
    void getAllAnimalsGetsRightAmountOfAnimals() {
        int AMOUNT_OF_ANIMALS = 1;
        AllAnimalDTO allAnimals = sut.getAllAnimals();
        ArrayList<AnimalDTO> animals = allAnimals.getAnimals();
        Assertions.assertEquals(AMOUNT_OF_ANIMALS, animals.size());
    }
}
