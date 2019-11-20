package nl.han.oose.sapporo.dto;

public class UserDTO {
    private int id;
    private String name;
    private String password;
    private String email;

    public UserDTO() {}

    public UserDTO(String name, String password, String email, int id) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.id = id;
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

    public int getId() {
        return id;
    }
}
