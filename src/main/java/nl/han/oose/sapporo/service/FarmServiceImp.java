package nl.han.oose.sapporo.service;

import nl.han.oose.sapporo.dto.FarmDTO;
import nl.han.oose.sapporo.dto.PlotDTO;
import nl.han.oose.sapporo.dto.UserDTO;
import nl.han.oose.sapporo.persistence.IFarmDAO;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class FarmServiceImp implements IFarmService {
    private static final float PLOT_PRICE = 10000;
    private IFarmDAO farmDAO;
    private IPlotService plotService;

    @Inject
    public void setPlotService(IPlotService plotService) {
        this.plotService = plotService;
    }

    @Inject
    public void setFarmDAO(IFarmDAO farmDAO) {
        this.farmDAO = farmDAO;
    }

    @Override
    public FarmDTO getFarm(UserDTO user) {
        FarmDTO farm = farmDAO.getFarm(user);
        farm.setPlots(plotService.getFarmPlots(farm.getFarmID()));
        return farm;
    }

    @Override
    public FarmDTO createFarm(UserDTO userDTO) {
        farmDAO.checkIfUserHasAFarm(userDTO);
        FarmDTO farmDTO = new FarmDTO();
        List<PlotDTO> plots = new ArrayList<>();
        for (int x = 0; x < farmDTO.getWIDTH(); x++) {
            for (int y = 0; y < farmDTO.getHEIGHT(); y++) {
                PlotDTO plotDTO = new PlotDTO(x, y, PLOT_PRICE, false);
                plots.add(plotDTO);

                if (x < farmDTO.getWIDTH() / 2 && y < farmDTO.getHEIGHT() / 2) {
                    plotDTO.setPurchased(true);
                }
            }
        }
        farmDTO.setPlots(plots);
        return farmDAO.createFarm(farmDTO, userDTO);
    }
}
