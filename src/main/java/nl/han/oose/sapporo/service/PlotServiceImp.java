package nl.han.oose.sapporo.service;

import nl.han.oose.sapporo.dto.PlantDTO;
import nl.han.oose.sapporo.dto.PlotDTO;
import nl.han.oose.sapporo.dto.UserDTO;
import nl.han.oose.sapporo.dto.AllPlotDTO;
import nl.han.oose.sapporo.dto.AnimalDTO;
import nl.han.oose.sapporo.dto.FarmDTO;
import nl.han.oose.sapporo.persistence.IAnimalDAO;
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
    private IAnimalDAO animalDAO;
    private IFarmDAO farmDAO;
    private IInventoryService inventoryService;
    private IPlantService plantService;
    private IAnimalService animalService;
    private IActionService actionService;
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
    public void setAnimalDAO(IAnimalDAO animalDAO) {
        this.animalDAO = animalDAO;
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
    public void setAnimalService(IAnimalService animalService) {
        this.animalService = animalService;
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
            actionService.setAction(userDTO,PURCHASE_PLOT_ACTION_ID,null);
            return getAllPlots(userDTO);
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
        final int GIVE_WATER_ACTION_ID = 3;
        PlotDTO plotDTO =  plotDAO.getPlot(plotID);
        String affectedPlant = plantDAO.getname(plotDTO.getPlantID());
        if (inventoryService.checkIfPlayerHasEnoughWater(amount, user) && plotDAO.plotHasPlant(plotID)){
            int plotWater = plotDAO.getWater(plotID);
            int amountThatFits = calculateWaterThatFits(plotWater,amount,MINIMUM_PLOT_WATER,MAXIMUM_PLOT_WATER);
            if (amountThatFits >0){
                actionService.setAction(user,GIVE_WATER_ACTION_ID,affectedPlant);
            }
            inventoryService.lowerWater(amountThatFits, user);
            plotDAO.editWaterAvailable(amountThatFits, plotID);
            return plotDAO.getPlot(plotID);
        }
        return null;
    }

    @Override
    public AllPlotDTO placeAnimal(AnimalDTO animalDTO, int plotID, UserDTO userDTO) {
        final int BUY_ANIMAL_ACTION_ID = 6;
        if (inventoryService.checkIfPlayerHasEnoughSaldo(animalDTO.getPurchasePrice(),userDTO) && plotDAO.checkIfPlotIsEmpty(plotID)) {
            inventoryService.lowerSaldo(animalDTO.getPurchasePrice(), userDTO);
            inventoryService.lowerWater(START_WATER,userDTO);
            plotDAO.addAnimalToPlot(animalDTO, plotID);
            String affectedAnimal = animalDAO.getAnimal(animalDTO.getID());
            actionService.setAction(userDTO, BUY_ANIMAL_ACTION_ID, affectedAnimal);
            return getAllPlots(userDTO);
        }
        return null;
    }

    @Override
    public AllPlotDTO sellProduct(PlotDTO plotDTO, UserDTO userDTO, int plotID) {
        final int SELL_PRODUCT_ACTION_ID = 8;
        final int AGE = 0;
        if (animalService.animalProductIsCollectable(plotDAO.getPlot(plotID)) && plotDAO.plotHasAnimal(plotID)) {
            plotDAO.updateAge(plotID, AGE);
            int profit = animalDAO.getProductProfit(plotDTO.getAnimalID());
            inventoryService.increaseSaldo(profit, userDTO);
            String affectedAnimal = animalDAO.getAnimal(plotDTO.getAnimalID());
            actionService.setAction(userDTO, SELL_PRODUCT_ACTION_ID, affectedAnimal);
            return getAllPlots(userDTO);
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

    private AllPlotDTO getAllPlots(UserDTO userDTO) {
        FarmDTO farmDTO = farmDAO.getFarm(userDTO);
        return new AllPlotDTO(getFarmPlots(farmDTO.getFarmID()));
    }
}
