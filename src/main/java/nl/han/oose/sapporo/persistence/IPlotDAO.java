package nl.han.oose.sapporo.persistence;

import nl.han.oose.sapporo.dto.AnimalDTO;
import nl.han.oose.sapporo.dto.FarmDTO;
import nl.han.oose.sapporo.dto.PlantDTO;
import nl.han.oose.sapporo.dto.PlotDTO;

import java.util.ArrayList;

public interface IPlotDAO {
    void addPlantToPlot(PlantDTO plantDTO, int plotID);

    PlotDTO getPlot(int plotID);

    boolean checkIfPlotIsEmpty(int plotID);

    boolean checkIfPlotHasWater(int plotID);

    boolean plotIsPurchased(int plotID);

    void removeObjectsFromPlot(int plotID);

    void purchasePlot(int plotID);

    boolean plotHasPlant(int plotID);

    void insertPlots(FarmDTO farmDTO);

    ArrayList<PlotDTO> getFarmPlots(int farmID);

    void updateAge(int plotID, int age);

    void changeStatus(int plotID, String status);

    void editWaterAvailable(int amount, int plotID);

    void createSilo(FarmDTO farmDTO);

    int getWater(int plotID);

    void replacePlantsOnAllPlots(int plantIDToDelete, int plantIDToReplaceWith);

    void addAnimalToPlot(AnimalDTO animalDTO, int plotID);

    boolean plotHasAnimal(int plotID);

    void replaceAnimalsOnAllPlots(int animalIDToDelete, int animalIDToReplaceWith);

    boolean plotHasWaterResource(int plotID);

    void createWell(FarmDTO farmDTO);
}
