package nl.han.oose.sapporo.service;

import nl.han.oose.sapporo.dto.AllPlantDTO;
import nl.han.oose.sapporo.dto.PlotDTO;

public interface IPlantService {
    AllPlantDTO getAllPlants();

    int getMaximumWater(int plantID);

    boolean plantFullGrown(PlotDTO plotDTO);
}
