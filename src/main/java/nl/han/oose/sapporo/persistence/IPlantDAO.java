package nl.han.oose.sapporo.persistence;

import nl.han.oose.sapporo.dto.AllPlantDTO;
import nl.han.oose.sapporo.dto.PlantDTO;

public interface IPlantDAO {
    AllPlantDTO getAllPlants();

    boolean checkIfPlantFullGrown(PlantDTO plantDTO);

    int getProfit(int id);
}
