package nl.han.oose.sapporo.persistence;

import nl.han.oose.sapporo.dto.AllAnimalDTO;

public interface IAnimalDAO {
    AllAnimalDTO getAllAnimals();

    void deleteAnimal(int animalIDToDelete);
}
