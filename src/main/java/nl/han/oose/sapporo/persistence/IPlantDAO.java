package nl.han.oose.sapporo.persistence;

import nl.han.oose.sapporo.dto.AllPlantDTO;
import nl.han.oose.sapporo.dto.PlantDTO;
import nl.han.oose.sapporo.dto.PlotDTO;

public interface IPlantDAO {
    AllPlantDTO getAllPlants();

    boolean checkIfPlantFullGrown(PlotDTO plotDTO);

    int getProfit(int id);
}
