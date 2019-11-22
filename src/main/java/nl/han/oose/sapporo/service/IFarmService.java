package nl.han.oose.sapporo.service;


import nl.han.oose.sapporo.dto.FarmDTO;
import nl.han.oose.sapporo.dto.UserDTO;

public interface IFarmService {
    void addFarm(FarmDTO farmDTO, UserDTO userDTO);
}
