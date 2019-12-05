package nl.han.oose.sapporo.service;

import nl.han.oose.sapporo.dto.InventoryDTO;
import nl.han.oose.sapporo.dto.UserDTO;

public interface IInventoryService {
    Boolean checkSaldo(float amount, UserDTO userDTO);

    void lowerSaldo(float amount, UserDTO userDTO);

    void increaseSaldo(float amount, UserDTO userDTO);

    void createInventory(UserDTO user);

    InventoryDTO getInventory(UserDTO user);
}
