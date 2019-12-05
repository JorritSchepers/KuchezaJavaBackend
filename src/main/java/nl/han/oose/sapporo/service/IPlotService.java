package nl.han.oose.sapporo.service;

import nl.han.oose.sapporo.dto.AllPlotDTO;
import nl.han.oose.sapporo.dto.PlantDTO;
import nl.han.oose.sapporo.dto.PlotDTO;
import nl.han.oose.sapporo.dto.UserDTO;

import java.util.ArrayList;

public interface IPlotService {
    PlotDTO placePlant(PlantDTO plantDTO, int plotID, UserDTO userDTO);

    PlotDTO harvestPlant(PlantDTO plantDTO, UserDTO user, int plotID);

    ArrayList<PlotDTO> getFarmPlots(int farmID);

    AllPlotDTO purchasePlot(int plotID, UserDTO userDTO);
}
