package nl.han.oose.sapporo.service;

import nl.han.oose.sapporo.dto.AllWaterSourceDTO;
import nl.han.oose.sapporo.persistence.BuildingDAO;

import javax.inject.Inject;

public class BuildingServiceImp implements BuildingService {
    private BuildingDAO buildingDAO;

    @Inject
    public void setBuildingDAO(BuildingDAO buildingDAO) {
        this.buildingDAO = buildingDAO;
    }

    @Override
    public AllWaterSourceDTO getAllWaterSources() {
        return buildingDAO.getAllWaterSources();
    }
}
