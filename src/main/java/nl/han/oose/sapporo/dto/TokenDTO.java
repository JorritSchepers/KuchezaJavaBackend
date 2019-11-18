package nl.han.oose.sapporo.dto;

import javax.xml.registry.infomodel.User;

public class TokenDTO {
    UserDTO user;
    String token;

    public TokenDTO(UserDTO user, String token) {
        this.user = user;
        this.token = token;
    }
}
