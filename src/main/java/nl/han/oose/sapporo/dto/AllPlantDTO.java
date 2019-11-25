package nl.han.oose.sapporo.dto;

import java.util.ArrayList;

public class AllPlantDTO {
    private ArrayList<PlantDTO> plants;

    public AllPlantDTO(ArrayList<PlantDTO> plants) {
        this.plants = plants;
    }

    public ArrayList<PlantDTO> getPlants() {
        return plants;
    }
}