package nl.han.oose.sapporo.service;

import nl.han.oose.sapporo.dto.TokenDTO;
import nl.han.oose.sapporo.dto.UserDTO;
import nl.han.oose.sapporo.persistence.IAccountDAO;

import javax.inject.Inject;

public class AccountServiceImp implements IAccountService {
    private IAccountDAO accountDAO;

    @Inject
    public void setAccountDAO(IAccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    private TokenDTO generateRandomToken(UserDTO userDTO){
        return new TokenDTO();
    }

    @Override
    public TokenDTO registerUser(UserDTO userDTO) {
        accountDAO.addUser(userDTO);
        return generateRandomToken(userDTO);
    }
}
