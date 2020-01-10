package nl.han.oose.sapporo.service;

import nl.han.oose.sapporo.dto.AllAnimalDTO;
import nl.han.oose.sapporo.dto.PlotDTO;

public interface IAnimalService {

    AllAnimalDTO getAllAnimals();

    boolean animalProductIsCollectable(PlotDTO plotDTO);

    void deleteAnimal(int animalIDToDelete);
}
