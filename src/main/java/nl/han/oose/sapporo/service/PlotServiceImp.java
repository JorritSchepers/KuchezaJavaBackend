package nl.han.oose.sapporo.service;

import nl.han.oose.sapporo.dto.PlantDTO;
import nl.han.oose.sapporo.dto.PlotDTO;
import nl.han.oose.sapporo.dto.UserDTO;
import nl.han.oose.sapporo.persistence.IPlotDAO;
import nl.han.oose.sapporo.persistence.exception.PlantNotGrownException;

import javax.enterprise.inject.Default;
import javax.inject.Inject;

@Default
public class PlotServiceImp implements IPlotService {
    private IPlotDAO plotDAO;
    private IInventoryService inventoryService;
    private IPlantService plantService;

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

    //TODO Implement methods that are called, return plot that is harvested!
    @Override
    public PlotDTO harvesPlot(PlantDTO plantDTO,UserDTO user, int plotID) {
        if (plantService.plantFullGrown(plantDTO)){
            plotDAO.removeObjectsFromPlot(plotID);
            inventoryService.increaseSaldo(plantDTO.getProfit(),user);
            return plotDAO.getPlot(plotID);
        } else  {
            System.out.println("plant is niet gegroeid");
            throw new PlantNotGrownException();
        }
    }
}