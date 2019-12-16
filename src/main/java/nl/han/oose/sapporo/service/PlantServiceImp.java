package nl.han.oose.sapporo.service;

import nl.han.oose.sapporo.dto.AllPlantDTO;
import nl.han.oose.sapporo.dto.PlotDTO;
import nl.han.oose.sapporo.dto.UserDTO;
import nl.han.oose.sapporo.persistence.IPlantDAO;
import nl.han.oose.sapporo.persistence.exception.PlantNotGrownException;

import javax.enterprise.inject.Default;
import javax.inject.Inject;
import java.util.ArrayList;

@Default
public class PlantServiceImp implements IPlantService {
    private IPlantDAO plantDAO;
    private IAdminService adminService;
    private IPlotService plotService;

    @Inject
    public void setPlantDAO(IPlantDAO plantDAO) {
        this.plantDAO = plantDAO;
    }

    @Inject
    public void setAdminService(IAdminService adminService) { this.adminService = adminService; }

    @Inject
    public void setPlotService(IPlotService plotService) { this.plotService = plotService; }

    @Override
    public AllPlantDTO getAllPlants() {
        return plantDAO.getAllPlants();
    }

    @Override
    public boolean plantFullGrown(PlotDTO plotDTO) {
        if (!plantDAO.checkIfPlantFullGrown(plotDTO)) {
            throw new PlantNotGrownException();
        }
        return true;
    }

    @Override
    public void deletePlant(UserDTO user, int plantIDToDelete, int plantIDToReplaceWith) {
        adminService.checkIfUserIsAdmin(user);
        plotService.replacePlantsOnAllPlots(plantIDToDelete, plantIDToReplaceWith);
        plantDAO.deletePlant(plantIDToDelete);
    }

    @Override
    public int getMaximumWater(int plantID) {
        return plantDAO.getMaximumWater(plantID);
    }
}
