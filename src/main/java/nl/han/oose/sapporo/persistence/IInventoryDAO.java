package nl.han.oose.sapporo.persistence;

import nl.han.oose.sapporo.dto.UserDTO;

public interface IInventoryDAO {
    boolean checkSaldo(float amount, UserDTO userDTO);

    void lowerSaldo(float amount, UserDTO userDTO);

    void increaseSaldo(float amount, UserDTO userDTO);

    void createInventory(UserDTO user);
}
