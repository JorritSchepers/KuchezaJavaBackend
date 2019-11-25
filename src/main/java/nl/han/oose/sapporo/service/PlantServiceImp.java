package nl.han.oose.sapporo.service;

import nl.han.oose.sapporo.dto.AllPlantDTO;
import nl.han.oose.sapporo.dto.PlantDTO;
import nl.han.oose.sapporo.persistence.IPlantDAO;
import nl.han.oose.sapporo.persistence.exception.PlantNotGrownException;

import javax.enterprise.inject.Default;
import javax.inject.Inject;

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
    public boolean plantFullGrown(PlantDTO plantDTO) {
        if(!plantDAO.checkIfPlantFullGrown(plantDTO)){
            throw new PlantNotGrownException();
        }
        return  true;
    }
}
