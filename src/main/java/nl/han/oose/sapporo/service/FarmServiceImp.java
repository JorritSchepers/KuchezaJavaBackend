package nl.han.oose.sapporo.service;

import nl.han.oose.sapporo.dto.FarmDTO;
import nl.han.oose.sapporo.dto.PlotDTO;
import nl.han.oose.sapporo.dto.UserDTO;
import nl.han.oose.sapporo.persistence.IFarmDAO;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class FarmServiceImp implements IFarmService {
    private static final int FARM_SIZE = 10;
    private static final float PLOT_PRICE = 10;
    IFarmDAO farmDAO;

    @Inject
    @Override
    public void setFarm(IFarmDAO farmDAO){
        this.farmDAO = farmDAO;
    }

    @Override
    public FarmDTO createFarm(UserDTO userDTO) {
        farmDAO.checkIfUserHasAFarm(userDTO);

        FarmDTO farmDTO = new FarmDTO();
        List<PlotDTO> plots = new ArrayList<PlotDTO>();
        for(int x = 0; x < FARM_SIZE; x++) {
            for(int y = 0; y < FARM_SIZE; y++) {
                PlotDTO plotDTO = new PlotDTO(x,y,PLOT_PRICE,false);
                plots.add(plotDTO);

                if(x < FARM_SIZE/2 && y < FARM_SIZE) {
                    plotDTO.setPurchased(true);
                }
            }
        }
        farmDTO.setPlots(plots);

        return farmDAO.createFarm(farmDTO, userDTO);
    }
}
