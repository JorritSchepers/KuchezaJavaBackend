package nl.han.oose.sapporo.persistence;

import nl.han.oose.sapporo.dto.LoginDTO;
import nl.han.oose.sapporo.dto.UserDTO;

public interface IAccountDAO {
    void addUser(UserDTO userDTO);

    UserDTO checkUser(LoginDTO loginDTO);

    String hexInformation(String information);
}

