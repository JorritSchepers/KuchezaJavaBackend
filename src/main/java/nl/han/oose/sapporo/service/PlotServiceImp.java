package nl.han.oose.sapporo.service;

import nl.han.oose.sapporo.dto.PlantDTO;
import nl.han.oose.sapporo.dto.PlotDTO;
import nl.han.oose.sapporo.dto.UserDTO;
import nl.han.oose.sapporo.persistence.IPlotDAO;

import javax.enterprise.inject.Default;
import javax.inject.Inject;

@Default
public class PlotServiceImp implements IPlotService {
    private IPlotDAO plotDAO;
    private IInventoryService inventoryService;
    private final int FARM_SIZE = 10;

    @Inject
    public void setPlotDAO(IPlotDAO plotDAO) {
        this.plotDAO = plotDAO;
    }

    @Inject
    public void setInventoryService(IInventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @Override
    public PlotDTO placePlant(PlantDTO plantDTO, int plotID, UserDTO userDTO) {
        if (inventoryService.checkSaldo(plantDTO.getPurchasePrice(),userDTO)) {
            inventoryService.lowerSaldo(plantDTO.getPurchasePrice(), userDTO);
            plotDAO.addPlantToPlot(plantDTO, plotID);
            return plotDAO.getPlot(plotID);
        }
        return null;
    }

    @Override
    public void createPlot(){
        for(int x = 0; x < FARM_SIZE; x++) {
            for(int y = 0; y < FARM_SIZE; y++) {
                plotDAO.createPlot(x, y);

            }
        }
    }
}