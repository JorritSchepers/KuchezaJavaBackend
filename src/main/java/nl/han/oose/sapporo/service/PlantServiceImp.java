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

    @Inject
    public void setPlantDAO(IPlantDAO plantDAO) {
        this.plantDAO = plantDAO;
    }

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
        plantDAO.deletePlant(plantIDToDelete);
    }

    @Override
    public int getMaximumWater(int plantID) {
        return plantDAO.getMaximumWater(plantID);
    }
}
