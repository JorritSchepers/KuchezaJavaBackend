package nl.han.oose.sapporo.persistence;

import nl.han.oose.sapporo.dto.FarmDTO;
import nl.han.oose.sapporo.dto.UserDTO;

public interface IfarmDAO {
    public FarmDTO getFarm(UserDTO user);
}
