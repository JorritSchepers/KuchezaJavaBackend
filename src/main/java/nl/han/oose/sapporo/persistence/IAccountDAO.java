package nl.han.oose.sapporo.persistence;

import nl.han.oose.sapporo.dto.UserDTO;

public interface IAccountDAO {
    void addUser(UserDTO userDTO);

    String hexInformation(String information);
}

