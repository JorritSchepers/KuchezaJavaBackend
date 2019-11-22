package nl.han.oose.sapporo.service;

import nl.han.oose.sapporo.dto.FarmDTO;
import nl.han.oose.sapporo.dto.UserDTO;
import nl.han.oose.sapporo.persistence.IFarmDAO;

import javax.inject.Inject;

public class FarmServiceImp implements IFarmService {
    IFarmDAO farmDAO;

    @Inject
    public void setFarm(IFarmDAO farmDAO){
        this.farmDAO = farmDAO;
    }

    @Override
    public void addFarm(FarmDTO farmDTO, UserDTO userDTO) {
        farmDAO.addFarm(farmDTO, userDTO);
    }
}
