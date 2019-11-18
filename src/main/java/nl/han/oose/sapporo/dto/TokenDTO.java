package nl.han.oose.sapporo.dto;

public class TokenDTO {
    UserDTO user;
    String token;

    public TokenDTO(UserDTO user, String token) {
        this.user = user;
        this.token = token;
    }
}
