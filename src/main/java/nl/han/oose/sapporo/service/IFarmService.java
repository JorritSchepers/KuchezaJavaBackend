package nl.han.oose.sapporo.service;


import nl.han.oose.sapporo.dto.FarmDTO;
import nl.han.oose.sapporo.dto.UserDTO;

public interface IFarmService {

    FarmDTO createFarm(UserDTO userDTO);

    FarmDTO getFarm(UserDTO user);
}
