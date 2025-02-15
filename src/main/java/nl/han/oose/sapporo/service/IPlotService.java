package nl.han.oose.sapporo.service;

import nl.han.oose.sapporo.dto.*;

import java.util.ArrayList;

public interface IPlotService {
    PlotDTO placePlant(PlantDTO plantDTO, int plotID, UserDTO userDTO);

    PlotDTO harvestPlant(PlotDTO plotDTO, UserDTO user, int plotID);

    ArrayList<PlotDTO> getFarmPlots(int farmID);

    void updateAge(int plotID, int age);

    void clearPlot(UserDTO user, int plotID);

    AllPlotDTO purchasePlot(int plotID, UserDTO userDTO);

    PlotDTO changeStatus(int plotID, String status);

    void replacePlantsOnAllPlots(int plantIDToDelete, int plantIDToReplaceWith);

    PlotDTO editWater(UserDTO user, int plotID, int amount, boolean removeFromInventory);

    AllPlotDTO placeAnimal(AnimalDTO animalDTO, int plotID, UserDTO userDTO);

    AllPlotDTO sellProduct(PlotDTO plotDTO, UserDTO user, int plotID);

    void replaceAnimalsOnAllPlots(int animalIDToDelete, int animalIDToReplaceWith);
}
