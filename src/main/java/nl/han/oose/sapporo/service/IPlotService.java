package nl.han.oose.sapporo.service;

import nl.han.oose.sapporo.dto.*;

import java.util.ArrayList;

public interface IPlotService {
    PlotDTO placePlant(PlantDTO plantDTO, int plotID, UserDTO userDTO);

    PlotDTO harvestPlant(PlotDTO plotDTO, UserDTO user, int plotID);

    ArrayList<PlotDTO> getFarmPlots(int farmID);

    void updageAge(int plotID, int age);

    AllPlotDTO purchasePlot(int plotID, UserDTO userDTO);

    PlotDTO editWater(UserDTO user, int plotID, int amount);

    AllPlotDTO placeAnimal(AnimalDTO animalDTO, int plotID, UserDTO userDTO);
}
