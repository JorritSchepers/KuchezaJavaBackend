package nl.han.oose.sapporo.dto;

public class TokenDTO {
    private UserDTO user;
    private String token;

    public TokenDTO(UserDTO user, String token) {
        this.user = user;
        this.token = token;
    }

    public UserDTO getUser() {
        return user;
    }

    public String getToken() {
        return token;
    }
}
