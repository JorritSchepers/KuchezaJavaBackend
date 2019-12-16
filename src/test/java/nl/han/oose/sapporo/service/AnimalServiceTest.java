package nl.han.oose.sapporo.service;

import nl.han.oose.sapporo.dto.AllAnimalDTO;
import nl.han.oose.sapporo.persistence.IAnimalDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class AnimalServiceTest {
    private AnimalServiceImp sut = new AnimalServiceImp();
    private IAnimalDAO animalDAO;
    private AllAnimalDTO allAnimalDTO = new AllAnimalDTO(null);

    AnimalServiceTest() {
        animalDAO = Mockito.mock(IAnimalDAO.class);
        Mockito.when(animalDAO.getAllAnimals()).thenReturn(allAnimalDTO);
        sut.setAnimalDAO(animalDAO);
    }

    @Test
    void getAllAnimalsCallsGetAllAnimals() {
        sut.getAllAnimals();
        Mockito.verify(animalDAO, Mockito.times(1)).getAllAnimals();
    }

    @Test
    void getAllAnimalsReturnsRightGetAllAnimals() {
        Assertions.assertEquals(allAnimalDTO, sut.getAllAnimals());
    }
}
