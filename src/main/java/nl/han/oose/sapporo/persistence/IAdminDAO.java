package nl.han.oose.sapporo.persistence;

import nl.han.oose.sapporo.dto.UserDTO;

public interface IAdminDAO {
    boolean isAdmin(UserDTO userDTO);
}
