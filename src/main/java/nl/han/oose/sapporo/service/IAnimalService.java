package nl.han.oose.sapporo.service;

import nl.han.oose.sapporo.dto.AllAnimalDTO;
import nl.han.oose.sapporo.dto.PlotDTO;
import nl.han.oose.sapporo.dto.UserDTO;

public interface IAnimalService {

    AllAnimalDTO getAllAnimals();

    boolean animalProductIsCollectable(PlotDTO plotDTO);
    
    void deleteAnimal(int animalIDToDelete);
}
