package nl.han.oose.sapporo.dto;

import java.util.ArrayList;

public class AllAnimalDTO {

    private ArrayList<AnimalDTO> animals;

    public AllAnimalDTO(ArrayList<AnimalDTO> animals) {
        this.animals = animals;
    }

    public ArrayList<AnimalDTO> getAnimals() {
        return animals;
    }
}
