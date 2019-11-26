package nl.han.oose.sapporo.persistence;

import nl.han.oose.sapporo.dto.FarmDTO;
import nl.han.oose.sapporo.dto.UserDTO;

import javax.inject.Inject;

public interface IFarmDAO {
    @Inject
    void setPlotDAO(IPlotDAO plotDAO);

    FarmDTO getFarm(UserDTO user);

    FarmDTO createFarm(FarmDTO farmDTO, UserDTO userDTO);

    void checkIfUserHasAFarm(UserDTO userDTO);
}
