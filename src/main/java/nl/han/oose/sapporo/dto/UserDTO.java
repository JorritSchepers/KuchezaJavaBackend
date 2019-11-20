package nl.han.oose.sapporo.dto;

public class UserDTO {
    private int iD;
    private String name;
    private String password;
    private String email;

    public UserDTO() {}

    public UserDTO(String name, String password, String email, int iD) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.iD = iD;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public int getiD() {
        return iD;
    }
}
