package nl.han.oose.sapporo.service;

import nl.han.oose.sapporo.dto.AllAnimalDTO;
import nl.han.oose.sapporo.dto.UserDTO;

public interface IAnimalService {

    AllAnimalDTO getAllAnimals();

    void deleteAnimal(int animalIDToDelete);
}
