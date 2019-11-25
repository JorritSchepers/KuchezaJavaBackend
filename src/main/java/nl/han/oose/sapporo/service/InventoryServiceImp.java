package nl.han.oose.sapporo.service;

import nl.han.oose.sapporo.dto.UserDTO;
import nl.han.oose.sapporo.persistence.IInventoryDAO;

import javax.inject.Inject;

public class InventoryServiceImp implements  IInventoryService {
    IInventoryDAO inventoryDAO;

    @Inject
    public void setInventoryDAO(IInventoryDAO inventoryDAO) {
        this.inventoryDAO = inventoryDAO;
    }

    @Override
    public Boolean checkSaldo(float amount, UserDTO userDTO) {
        return inventoryDAO.checkSaldo(amount,userDTO);
    }

    @Override
    public void lowerSaldo(float amount, UserDTO userDTO) {
        inventoryDAO.lowerSaldo(amount,userDTO);
    }

    @Override
    public void increaseSaldo(float amount, UserDTO userDTO) {
        inventoryDAO.increaseSaldo(amount,userDTO);
    }
}
