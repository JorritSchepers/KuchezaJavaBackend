package nl.han.oose.sapporo.persistence;

import nl.han.oose.sapporo.dto.FarmDTO;
import nl.han.oose.sapporo.dto.PlantDTO;
import nl.han.oose.sapporo.dto.PlotDTO;

import java.util.ArrayList;

public interface IPlotDAO {
    void addPlantToPlot(PlantDTO plantDTO, int plotID);

    PlotDTO getPlot(int PlotID);

    boolean checkIfPlotIsEmpty(int plotID);

    void removeObjectsFromPlot(int plotID);

    boolean plotHasPlant(int plotID);

    void insertPlots(FarmDTO farmDTO);

    ArrayList<PlotDTO> getFarmPlots(int farmID);
}
