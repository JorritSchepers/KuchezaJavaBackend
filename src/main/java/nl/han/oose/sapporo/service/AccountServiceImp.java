package nl.han.oose.sapporo.service;

import nl.han.oose.sapporo.dto.LoginDTO;
import nl.han.oose.sapporo.dto.TokenDTO;
import nl.han.oose.sapporo.dto.UserDTO;
import nl.han.oose.sapporo.persistence.IAccountDAO;
import nl.han.oose.sapporo.service.functionalInterface.CustomUuid;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AccountServiceImp implements IAccountService {
    static private HashMap<String, UserDTO> tokens = new HashMap<String, UserDTO>();

    private CustomUuid customUuid;
    private IAccountDAO accountDAO;

    AccountServiceImp() {
        customUuid = () -> UUID.randomUUID().toString();
    }

    @Override
    public TokenDTO registerUser(UserDTO userDTO) {
        accountDAO.addUser(userDTO);
        return generateRandomToken(userDTO);
    }

    @Override
    public TokenDTO loginUser(LoginDTO loginDTO) {
        UserDTO user = accountDAO.checkUser(loginDTO);
        removeOldToken(user);
        return generateRandomToken(user);
    }

    private TokenDTO generateRandomToken(UserDTO userDTO){
        String token = customUuid.randomUUID();
        tokens.put(token,userDTO);
        return new TokenDTO(userDTO,token);
    }

    private void removeOldToken(UserDTO user) {
        String existingToken = getTokenByUser(user);
        if (existingToken != null) {
            tokens.remove(existingToken);
        }
    }

    private String getTokenByUser(UserDTO user) {
        for (Map.Entry<String, UserDTO> entry : tokens.entrySet()) {
            String key = entry.getKey();
            UserDTO u = entry.getValue();

            if (u.getName() == user.getName()) {
                return key;
            }
        }
        return null;
    }

    @Inject
    public void setAccountDAO(IAccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public void setCustomUuid(CustomUuid customUuid) {
        this.customUuid = customUuid;
    }
}