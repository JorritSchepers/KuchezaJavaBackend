package nl.han.oose.sapporo.service;

import nl.han.oose.sapporo.dto.*;
import nl.han.oose.sapporo.persistence.IFarmDAO;
import nl.han.oose.sapporo.persistence.IPlantDAO;
import nl.han.oose.sapporo.persistence.IPlotDAO;

import javax.enterprise.inject.Default;
import javax.inject.Inject;
import java.util.ArrayList;

@Default
public class PlotServiceImp implements IPlotService {
    private IPlotDAO plotDAO;
    private IPlantDAO plantDAO;
    private IFarmDAO farmDAO;
    private IInventoryService inventoryService;
    private IPlantService plantService;

    @Inject
    public void setPlantDAO(IPlantDAO plantDAO) {
        this.plantDAO = plantDAO;
    }

    @Inject
    public void setPlotDAO(IPlotDAO plotDAO) {
        this.plotDAO = plotDAO;
    }

    @Inject
    public void setFarmDAO(IFarmDAO farmDAO) {
        this.farmDAO = farmDAO;
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
    public PlotDTO harvestPlant(PlantDTO plantDTO, UserDTO user, int plotID) {
        if (plantService.plantFullGrown(plantDTO) && plotDAO.plotHasPlant(plotID)) {
            plotDAO.removeObjectsFromPlot(plotID);
            int profit = plantDAO.getProfit(plantDTO.getID());
            inventoryService.increaseSaldo(profit, user);
            return plotDAO.getPlot(plotID);
        }
        return null;
    }

    public AllPlotDTO purchasePlot(int plotID, UserDTO userDTO) {
        PlotDTO plotDTO = plotDAO.getPlot(plotID);
        if (inventoryService.checkSaldo(plotDTO.getPrice(), userDTO) && !plotDAO.plotIsPurchased(plotID)) {
            inventoryService.lowerSaldo(plotDTO.getPrice(), userDTO);
            plotDAO.purchasePlot(plotID);
            FarmDTO farmDTO = farmDAO.getFarm(userDTO);
            return new AllPlotDTO(getFarmPlots(farmDTO.getFarmID()));
        }
        return null;
    }

    @Override
    public ArrayList<PlotDTO> getFarmPlots(int farmID) {
        return plotDAO.getFarmPlots(farmID);
    }
}