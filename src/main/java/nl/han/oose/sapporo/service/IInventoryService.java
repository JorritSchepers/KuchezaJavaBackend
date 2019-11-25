package nl.han.oose.sapporo.service;

import nl.han.oose.sapporo.dto.PlantDTO;
import nl.han.oose.sapporo.dto.UserDTO;

public interface IInventoryService {
    Boolean checkSaldo(float amount, UserDTO userDTO);

    void lowerSaldo(float amount, UserDTO userDTO);

    void increaseSaldo(float amount, UserDTO userDTO);
}
