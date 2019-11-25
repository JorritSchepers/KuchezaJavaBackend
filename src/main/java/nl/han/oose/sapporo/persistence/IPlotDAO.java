package nl.han.oose.sapporo.persistence;

import nl.han.oose.sapporo.dto.PlantDTO;
import nl.han.oose.sapporo.dto.PlotDTO;

public interface IPlotDAO {
    void addPlantToPlot(PlantDTO plantDTO, int plotID);

    PlotDTO getPlot(int PlotID);

    boolean checkIfPlotIsEmpty(int plotID);

    void removeObjectsFromPlot(int plotID);
}
