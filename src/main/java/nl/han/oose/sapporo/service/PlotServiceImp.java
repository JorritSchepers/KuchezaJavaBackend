package nl.han.oose.sapporo.service;

import nl.han.oose.sapporo.dto.PlantDTO;
import nl.han.oose.sapporo.dto.PlotDTO;
import nl.han.oose.sapporo.dto.UserDTO;
import nl.han.oose.sapporo.persistence.IPlantDAO;
import nl.han.oose.sapporo.persistence.IPlotDAO;

import javax.enterprise.inject.Default;
import javax.inject.Inject;
import java.util.ArrayList;

@Default
public class PlotServiceImp implements IPlotService {
    private IPlotDAO plotDAO;
    private IPlantDAO plantDAO;
    private IInventoryService inventoryService;
    private IPlantService plantService;
    private static final int MAGIC_WATER_NUMBER_CHANGE_LATER = 10;

    @Inject
    public void setPlantDAO(IPlantDAO plantDAO) {
        this.plantDAO = plantDAO;
    }

    @Inject
    public void setPlotDAO(IPlotDAO plotDAO) {
        this.plotDAO = plotDAO;
    }

    @Inject
    public void setInventoryService(IInventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @Inject
    public void setPlantService(IPlantService plantService) {
        this.plantService = plantService;
    }

    @Override
    public PlotDTO placePlant(PlantDTO plantDTO, int plotID, UserDTO userDTO) {
        if (inventoryService.checkSaldo(plantDTO.getPurchasePrice(),userDTO) && plotDAO.checkIfPlotIsEmpty(plotID)) {
            inventoryService.lowerSaldo(plantDTO.getPurchasePrice(), userDTO);
            plotDAO.addPlantToPlot(plantDTO, plotID);
            return plotDAO.getPlot(plotID);
        }
        return null;
    }

    @Override
    public PlotDTO harvesPlant(PlantDTO plantDTO, UserDTO user, int plotID) {
        if (plantService.plantFullGrown(plantDTO) && plotDAO.plotHasPlant(plotID)) {
            plotDAO.removeObjectsFromPlot(plotID);
            int profit = plantDAO.getProfit(plantDTO.getID());
            inventoryService.increaseSaldo(profit, user);
            return plotDAO.getPlot(plotID);
        }
        return null;
    }

    @Override
    public ArrayList<PlotDTO> getFarmPlots(int farmID) {
        return plotDAO.getFarmPlots(farmID);
    }


    @Override
    public PlotDTO waterPlant(UserDTO user, int plotID) {
        if (inventoryService.checkWater(MAGIC_WATER_NUMBER_CHANGE_LATER, user) && plotDAO.plotHasPlant(plotID)){
            inventoryService.lowerWater(MAGIC_WATER_NUMBER_CHANGE_LATER, user);
            plotDAO.increaseWaterAvailable(MAGIC_WATER_NUMBER_CHANGE_LATER, plotID);
            return plotDAO.getPlot(plotID);
        }
        return null;
    }


}