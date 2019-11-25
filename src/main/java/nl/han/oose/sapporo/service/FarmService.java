package nl.han.oose.sapporo.service;

import nl.han.oose.sapporo.dto.FarmDTO;
import nl.han.oose.sapporo.dto.UserDTO;
import nl.han.oose.sapporo.persistence.IfarmDAO;

import javax.inject.Inject;

public class FarmService implements IFarmService {
    IfarmDAO farmDAO;
    IPlotService plotService;

    @Inject
    public void setPlotService(IPlotService plotService) {
        this.plotService = plotService;
    }

    @Inject
    public void setIfarmDAO(IfarmDAO ifarmDAO) {
        this.farmDAO = ifarmDAO;
    }

    @Override
    public FarmDTO getFarm(UserDTO user) {
        FarmDTO farm = farmDAO.getFarm(user);
        farm.setPlots(plotService.getFarmPlots(farm.getFarmID()));
        return farm;
    }
}
