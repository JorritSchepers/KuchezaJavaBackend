package nl.han.oose.sapporo.persistence;

import nl.han.oose.sapporo.dto.FarmDTO;
import nl.han.oose.sapporo.dto.UserDTO;

public interface IFarmDAO {
    void checkIfUserHasAFarm(UserDTO userDTO);

    FarmDTO createFarm(FarmDTO farmDTO, UserDTO userDTO);
}
