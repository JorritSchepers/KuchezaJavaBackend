package nl.han.oose.sapporo.dto;

public class UserDTO {
    // TODO Volledige naam of username?
    private int id;
    private String name;
    private String password;
    private String email;

    public UserDTO() {}

    public UserDTO(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
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
}
