package nl.han.oose.sapporo.service;

import nl.han.oose.sapporo.dto.InventoryDTO;
import nl.han.oose.sapporo.dto.UserDTO;
import nl.han.oose.sapporo.persistence.IInventoryDAO;

import javax.inject.Inject;

public class InventoryServiceImp implements IInventoryService {
    private IInventoryDAO inventoryDAO;

    @Inject
    public void setInventoryDAO(IInventoryDAO inventoryDAO) {
        this.inventoryDAO = inventoryDAO;
    }

    @Override
    public boolean checkSaldo(float amount, UserDTO userDTO) {
        return inventoryDAO.checkSaldo(amount, userDTO);
    }

    @Override
    public void lowerSaldo(float amount, UserDTO userDTO) {
        inventoryDAO.lowerSaldo(amount, userDTO);
    }

    @Override
    public void increaseSaldo(float amount, UserDTO userDTO) {
        inventoryDAO.increaseSaldo(amount, userDTO);
    }

    @Override
    public boolean checkWater(int amount, UserDTO userDTO) { return inventoryDAO.checkWater(amount, userDTO); }

    @Override
    public void lowerWater(int amount, UserDTO userDTO) { inventoryDAO.lowerWater(amount, userDTO); }

    @Override
    public void increaseWater(int amount, UserDTO userDTO) { inventoryDAO.increaseWater(amount, userDTO); }

    @Override
    public void createInventory(UserDTO user) {
        inventoryDAO.createInventory(user);
    }

    @Override
    public InventoryDTO getInventory(UserDTO user) {
        return inventoryDAO.getInventory(user);
    }
}
