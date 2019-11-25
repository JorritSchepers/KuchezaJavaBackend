package nl.han.oose.sapporo.service;

import nl.han.oose.sapporo.dto.FarmDTO;
import nl.han.oose.sapporo.dto.UserDTO;

public interface IFarmService {
    public FarmDTO getFarm(UserDTO user);
}
