package nl.han.oose.sapporo.service;

import nl.han.oose.sapporo.dto.*;
import nl.han.oose.sapporo.persistence.IFarmDAO;
import nl.han.oose.sapporo.persistence.IPlantDAO;
import nl.han.oose.sapporo.persistence.IPlotDAO;
import nl.han.oose.sapporo.service.exception.PlotIsAlreadyPurchasedException;

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
    private final int START_WATER = 25;
    private final int MINIMUM_PLOT_WATER = 0;
    private final int MAXIMUM_PLOT_WATER = 100;

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
        if (inventoryService.checkIfPlayerHasEnoughSaldo(plantDTO.getPurchasePrice(),userDTO) && plotDAO.checkIfPlotIsEmpty(plotID)) {
            inventoryService.lowerSaldo(plantDTO.getPurchasePrice(), userDTO);
            inventoryService.lowerWater(START_WATER,userDTO);
            plotDAO.addPlantToPlot(plantDTO, plotID);
            return plotDAO.getPlot(plotID);
        }
        return null;
    }

    @Override
    public PlotDTO harvestPlant(PlotDTO plotDTO, UserDTO user, int plotID) {
        if (plantService.plantFullGrown(plotDAO.getPlot(plotID)) && plotDAO.plotHasPlant(plotID)) {
            plotDAO.removeObjectsFromPlot(plotID);
            int profit = plantDAO.getProfit(plotDTO.getPlantID());
            inventoryService.increaseSaldo(profit, user);
            return plotDAO.getPlot(plotID);
        }
        return null;
    }

    @Override
    public AllPlotDTO purchasePlot(int plotID, UserDTO userDTO) {
        PlotDTO plotDTO = plotDAO.getPlot(plotID);
        if(plotDAO.plotIsPurchased(plotID)) {
            throw new PlotIsAlreadyPurchasedException();
        }
        if (inventoryService.checkIfPlayerHasEnoughSaldo(plotDTO.getPrice(), userDTO) && !plotDAO.plotIsPurchased(plotID)) {
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

    @Override
    public void updageAge(int plotID, int age) {
        plotDAO.updateAge(plotID,age);
    }

    @Override
    public PlotDTO editWater(UserDTO user, int plotID, int amount) {
        if (inventoryService.checkIfPlayerHasEnoughWater(amount, user) && plotDAO.plotHasPlant(plotID)){
            int plotWater = plotDAO.getWater(plotID);

            if(plotWater + amount < MINIMUM_PLOT_WATER) {
                int waterThatFits = -(MINIMUM_PLOT_WATER+plotWater);
                inventoryService.lowerWater(waterThatFits, user);
                plotDAO.editWaterAvailable(waterThatFits, plotID);
            } else if (plotWater + amount > 100) {
                int waterThatFits = MAXIMUM_PLOT_WATER-plotWater;
                inventoryService.lowerWater(waterThatFits, user);
                plotDAO.editWaterAvailable(waterThatFits, plotID);
            } else {
                inventoryService.lowerWater(amount, user);
                plotDAO.editWaterAvailable(amount, plotID);
            }

            return plotDAO.getPlot(plotID);
        }
        return null;
    }

    @Override
    public AllPlotDTO placeAnimal(AnimalDTO animalDTO, int plotID, UserDTO userDTO) {
        if (inventoryService.checkIfPlayerHasEnoughSaldo(animalDTO.getPurchasePrice(),userDTO) && plotDAO.checkIfPlotIsEmpty(plotID)) {
            inventoryService.lowerSaldo(animalDTO.getPurchasePrice(), userDTO);
            inventoryService.lowerWater(START_WATER,userDTO);
            plotDAO.addAnimalToPlot(animalDTO, plotID);
            FarmDTO farmDTO = farmDAO.getFarm(userDTO);
            return new AllPlotDTO(getFarmPlots(farmDTO.getFarmID()));
        }
        return null;
    }
}