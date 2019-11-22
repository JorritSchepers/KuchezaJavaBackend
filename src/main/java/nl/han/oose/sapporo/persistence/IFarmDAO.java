package nl.han.oose.sapporo.persistence;

import nl.han.oose.sapporo.dto.FarmDTO;
import nl.han.oose.sapporo.dto.UserDTO;

public interface IFarmDAO {
    FarmDTO addFarm(FarmDTO farmDTO, UserDTO userDTO);
}
