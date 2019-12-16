package nl.han.oose.sapporo.service;

import nl.han.oose.sapporo.dto.AllPlantDTO;
import nl.han.oose.sapporo.dto.PlotDTO;

public interface IPlantService {
    AllPlantDTO getAllPlants();

    boolean plantFullGrown(PlotDTO plotDTO);

    void deletePlant(int plantIDToDelete);

    int getMaximumWater(int plantID);
}
