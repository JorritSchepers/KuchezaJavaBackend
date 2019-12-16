package nl.han.oose.sapporo.service;

import nl.han.oose.sapporo.dto.AllPlotDTO;
import nl.han.oose.sapporo.dto.PlantDTO;
import nl.han.oose.sapporo.dto.PlotDTO;
import nl.han.oose.sapporo.dto.UserDTO;
import nl.han.oose.sapporo.dto.AnimalDTO;

import java.util.ArrayList;

public interface IPlotService {
    PlotDTO placePlant(PlantDTO plantDTO, int plotID, UserDTO userDTO);

    PlotDTO harvestPlant(PlotDTO plotDTO, UserDTO user, int plotID);

    ArrayList<PlotDTO> getFarmPlots(int farmID);

    void updateAge(int plotID, int age);

    AllPlotDTO purchasePlot(int plotID, UserDTO userDTO);

    PlotDTO editWater(UserDTO user, int plotID, int amount);

    void updatePlantsOnAllPlots(int plantIDToDelete, int plantIDToReplaceWith);

    AllPlotDTO placeAnimal(AnimalDTO animalDTO, int plotID, UserDTO userDTO);
}
