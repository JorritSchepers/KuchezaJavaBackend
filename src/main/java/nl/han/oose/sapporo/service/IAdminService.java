package nl.han.oose.sapporo.service;

import nl.han.oose.sapporo.dto.AllUsersDTO;
import nl.han.oose.sapporo.dto.UserDTO;

public interface IAdminService {
    void checkIfUserIsAdmin(UserDTO user);

    AllUsersDTO getAllNonAdminUsers(UserDTO user);
}
