package nl.han.oose.sapporo.service;

import nl.han.oose.sapporo.dto.TokenDTO;
import nl.han.oose.sapporo.dto.UserDTO;

public interface IAccountService {
    TokenDTO registerUser(UserDTO userDTO);
}