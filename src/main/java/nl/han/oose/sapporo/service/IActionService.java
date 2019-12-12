package nl.han.oose.sapporo.service;

import nl.han.oose.sapporo.dto.UserDTO;

public interface IActionService {
    void setAction(UserDTO user, int actionID, String affectedName);
}
