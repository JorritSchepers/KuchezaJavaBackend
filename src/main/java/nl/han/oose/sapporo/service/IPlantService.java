package nl.han.oose.sapporo.service;

import nl.han.oose.sapporo.dto.AllPlantDTO;
import nl.han.oose.sapporo.dto.PlantDTO;

public interface IPlantService {
    AllPlantDTO getAllPlants();

    boolean plantFullGrown(PlantDTO plantDTO);
}
