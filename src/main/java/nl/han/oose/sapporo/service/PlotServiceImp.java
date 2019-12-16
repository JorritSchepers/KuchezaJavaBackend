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
        if (plotDAO.plotHasPlant(plotID)) {
            System.out.println(plotDTO.getStatus());
            if(!plotDTO.getStatus().equals("Dead")) {
                int profit = plantDAO.getProfit(plotDTO.getPlantID());

                if(plotDTO.getStatus().equals("Dehydrated")) {
                    profit /= 2;
                }

                if(plantService.plantFullGrown(plotDAO.getPlot(plotID))) {
                    inventoryService.increaseSaldo(profit, user);
                    plotDAO.removeObjectsFromPlot(plotID);
                }
            } else {
                plotDAO.removeObjectsFromPlot(plotID);
            }
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

    public PlotDTO changeStatus(int plotID, String status) {
        plotDAO.changeStatus(plotID,status);
        return plotDAO.getPlot(plotID);
    }

    @Override
    public PlotDTO editWater(UserDTO user, int plotID, int amount) {
        if (inventoryService.checkIfPlayerHasEnoughWater(amount, user) && plotDAO.plotHasPlant(plotID)){
            PlotDTO plot = plotDAO.getPlot(plotID);
            int amountThatFits = calculateWaterThatFits(plot.getWaterAvailable(),amount,MINIMUM_PLOT_WATER,plantService.getMaximumWater(plot.getPlantID()));
            System.out.println(amountThatFits);

            inventoryService.lowerWater(amountThatFits, user);
            plotDAO.editWaterAvailable(amountThatFits, plotID);
            return plotDAO.getPlot(plotID);
        }
        return null;
    }

    public int calculateWaterThatFits(int originalAmount, int amountAdded, int min, int max) {
        if(originalAmount + amountAdded < min) {
            return -(min+originalAmount);
        } else if (originalAmount + amountAdded > max) {
            return max-originalAmount;
        } else {
            return amountAdded;
        }
    }
}