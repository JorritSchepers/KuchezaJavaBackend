package nl.han.oose.sapporo.persistence;

import nl.han.oose.sapporo.dto.AllAnimalDTO;
import nl.han.oose.sapporo.dto.PlotDTO;

import java.sql.ResultSet;

public interface IAnimalDAO {
    AllAnimalDTO getAllAnimals();

    boolean checkIfProductIsCollectable(PlotDTO plotDTO);

    int getProductProfit(int id);

    String getAnimal(int animalID);
}
