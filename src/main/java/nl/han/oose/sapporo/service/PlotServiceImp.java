package nl.han.oose.sapporo.service;

import nl.han.oose.sapporo.dto.PlantDTO;
import nl.han.oose.sapporo.dto.PlotDTO;
import nl.han.oose.sapporo.dto.UserDTO;
import nl.han.oose.sapporo.dto.AllPlotDTO;
import nl.han.oose.sapporo.dto.AnimalDTO;
import nl.han.oose.sapporo.dto.FarmDTO;
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
    private IActionService actionService;
    private final int START_WATER = 25;
    private final int MINIMUM_PLOT_WATER = 0;

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

    @Inject
    public void setActionService(IActionService actionService) {
        this.actionService = actionService;
    }

    @Override
    public PlotDTO placePlant(PlantDTO plantDTO, int plotID, UserDTO userDTO) {
        final int PLANT_SEED_ACTION_ID = 1;
        if (inventoryService.checkIfPlayerHasEnoughSaldo(plantDTO.getPurchasePrice(),userDTO) && plotDAO.checkIfPlotIsEmpty(plotID)) {
            inventoryService.lowerSaldo(plantDTO.getPurchasePrice(), userDTO);
            inventoryService.lowerWater(START_WATER,userDTO);
            plotDAO.addPlantToPlot(plantDTO, plotID);
            actionService.setAction(userDTO,PLANT_SEED_ACTION_ID,plantDTO.getName());
            return plotDAO.getPlot(plotID);
        }
        return null;
    }

    @Override
    public PlotDTO harvestPlant(PlotDTO plotDTO, UserDTO user, int plotID) {
        final int HARVEST_PLANT_ACTION_ID = 2;
        if (plantService.plantFullGrown(plotDAO.getPlot(plotID)) && plotDAO.plotHasPlant(plotID)) {
            String affectedPlant = plantDAO.getname(plotDTO.getPlantID());
            plotDAO.removeObjectsFromPlot(plotID);
            int profit = plantDAO.getProfit(plotDTO.getPlantID());
            inventoryService.increaseSaldo(profit, user);
            actionService.setAction(user,HARVEST_PLANT_ACTION_ID,affectedPlant);
            return plotDAO.getPlot(plotID);
        }
        return null;
    }

    @Override
    public AllPlotDTO purchasePlot(int plotID, UserDTO userDTO) {
        final int PURCHASE_PLOT_ACTION_ID = 4;
        PlotDTO plotDTO = plotDAO.getPlot(plotID);
        if(plotDAO.plotIsPurchased(plotID)) {
            throw new PlotIsAlreadyPurchasedException();
        }
        if (inventoryService.checkIfPlayerHasEnoughSaldo(plotDTO.getPrice(), userDTO) && !plotDAO.plotIsPurchased(plotID)) {
            inventoryService.lowerSaldo(plotDTO.getPrice(), userDTO);
            plotDAO.purchasePlot(plotID);
            FarmDTO farmDTO = farmDAO.getFarm(userDTO);
            actionService.setAction(userDTO,PURCHASE_PLOT_ACTION_ID,null);
            return new AllPlotDTO(getFarmPlots(farmDTO.getFarmID()));
        }
        return null;
    }

    @Override
    public ArrayList<PlotDTO> getFarmPlots(int farmID) {
        return plotDAO.getFarmPlots(farmID);
    }

    @Override
    public void updateAge(int plotID, int age) {
        plotDAO.updateAge(plotID,age);
    }

    @Override
    public PlotDTO editWater(UserDTO user, int plotID, int amount) {
        final int GIVE_WATER_ACTION_ID = 3;
        PlotDTO plotDTO =  plotDAO.getPlot(plotID);
        String affectedPlant = plantDAO.getname(plotDTO.getPlantID());
        if (inventoryService.checkIfPlayerHasEnoughWater(amount, user) && plotDAO.plotHasPlant(plotID)){
            PlotDTO plot = plotDAO.getPlot(plotID);
            int amountThatFits = calculateWaterThatFits(plot.getWaterAvailable(),amount,MINIMUM_PLOT_WATER,plantService.getMaximumWater(plot.getPlantID()));

            inventoryService.lowerWater(amountThatFits, user);
            plotDAO.editWaterAvailable(amountThatFits, plotID);
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

    private int calculateWaterThatFits(int originalAmount, int amountAdded, int min, int max) {
        if (originalAmount + amountAdded < min) {
            return -(min + originalAmount);
        } else if (originalAmount + amountAdded > max) {
            return max - originalAmount;
        } else {
            return amountAdded;
        }
    }

    @Override
    public void updatePlantsOnAllPlots(int plantIDToDelete, int plantIDToReplaceWith) {
        plotDAO.updatePlantsOnAllPlots(plantIDToDelete, plantIDToReplaceWith);
    }
}
