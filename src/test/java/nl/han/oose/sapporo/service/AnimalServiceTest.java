package nl.han.oose.sapporo.service;

import nl.han.oose.sapporo.dto.AllAnimalDTO;
import nl.han.oose.sapporo.dto.PlotDTO;
import nl.han.oose.sapporo.persistence.IAnimalDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class AnimalServiceTest {
    private AnimalServiceImp sut = new AnimalServiceImp();
    private IAnimalDAO animalDAO;
    private AllAnimalDTO allAnimalDTO = new AllAnimalDTO(null);
    private final PlotDTO PLOT = new PlotDTO(1, 1, 1, 0, 0, 1, 100, true, 10, 30);

    AnimalServiceTest() {
        animalDAO = Mockito.mock(IAnimalDAO.class);
        sut.setAnimalDAO(animalDAO);

        Mockito.when(animalDAO.getAllAnimals()).thenReturn(allAnimalDTO);
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

    @Test
    void animalProductIsCollectableCallsCheckIfProductIsCollectable() {
        Mockito.when(animalDAO.checkIfProductIsCollectable(PLOT)).thenReturn(true);
        sut.animalProductIsCollectable(PLOT);
        Mockito.verify(animalDAO, Mockito.times(1)).checkIfProductIsCollectable(PLOT);
    }

    @Test
    void animalProductIsCollectableThrowsExceptionWhenNotCollactable() {
        final PlotDTO PLOT_WITHOUT_COLLECTABLE_PRODUCT = new PlotDTO(1, 1, 1, 1, 0, 1, 0, 0);
        Mockito.when(animalDAO.checkIfProductIsCollectable(PLOT_WITHOUT_COLLECTABLE_PRODUCT)).thenReturn(true);
        Assertions.assertDoesNotThrow(() -> {
            sut.animalProductIsCollectable(PLOT_WITHOUT_COLLECTABLE_PRODUCT);
        });
      }

    void deleteAnimalCallsDeleteAnimal(){
        final int DELETEID =1;
        sut.deleteAnimal(DELETEID);
        Mockito.verify(animalDAO,Mockito.times(1)).deleteAnimal(DELETEID);
    }
}
