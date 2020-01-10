package nl.han.oose.sapporo.service;

import nl.han.oose.sapporo.dto.AllWaterSourceDTO;
import nl.han.oose.sapporo.persistence.IBuildingDAO;

import javax.inject.Inject;

public class BuildingServiceImp implements IBuildingService {
    private IBuildingDAO IBuildingDAO;

    @Inject
    public void setBuildingDAO(IBuildingDAO IBuildingDAO) {
        this.IBuildingDAO = IBuildingDAO;
    }

    @Override
    public AllWaterSourceDTO getAllWaterSources() {
        return IBuildingDAO.getAllWaterSources();
    }
}
