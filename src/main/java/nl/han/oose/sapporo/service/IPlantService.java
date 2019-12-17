package nl.han.oose.sapporo.service;

import nl.han.oose.sapporo.dto.AllPlantDTO;
import nl.han.oose.sapporo.dto.PlotDTO;
import nl.han.oose.sapporo.dto.UserDTO;

public interface IPlantService {
    AllPlantDTO getAllPlants();

    int getMaximumWater(int plantID);

    boolean plantFullGrown(PlotDTO plotDTO);

    void deletePlant(UserDTO user, int plantIDToDelete, int plantIDToReplaceWith);

    int getMaximumWater(int plantID);
}
