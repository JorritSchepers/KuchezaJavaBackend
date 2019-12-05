package nl.han.oose.sapporo.service;

import nl.han.oose.sapporo.dto.AllUsersDTO;
import nl.han.oose.sapporo.dto.UserDTO;
import nl.han.oose.sapporo.persistence.IAdminDAO;
import nl.han.oose.sapporo.service.exception.UserIsNotAnAdministratorException;

import javax.inject.Inject;

public class AdminServiceImp implements IAdminService {
    private IAdminDAO adminDAO;

    @Inject
    public void setAdminDAO(IAdminDAO adminDAO) {
        this.adminDAO = adminDAO;
    }

    @Override
    public void checkIfUserIsAdmin(UserDTO user) {
        if (!adminDAO.isAdmin(user)) throw new UserIsNotAnAdministratorException();
    }

    @Override
    public AllUsersDTO getAllNonAdminUsers(UserDTO user) {
        checkIfUserIsAdmin(user);
        return adminDAO.getAllNonAdminUsers();
    }
}
