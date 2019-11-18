package nl.han.oose.sapporo.dto;

import javax.xml.registry.infomodel.User;

public class UserDTO {
    private String name;
    private String password;
    private String email;

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public UserDTO(){

    }

    public UserDTO(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }
}
