package nl.han.oose.sapporo.service;


import nl.han.oose.sapporo.dto.FarmDTO;
import nl.han.oose.sapporo.dto.UserDTO;
import nl.han.oose.sapporo.persistence.IFarmDAO;

import javax.inject.Inject;

public interface IFarmService {
    @Inject
    void setFarm(IFarmDAO farmDAO);

    FarmDTO createFarm(UserDTO userDTO);
}
