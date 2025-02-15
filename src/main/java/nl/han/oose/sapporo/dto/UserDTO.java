package nl.han.oose.sapporo.dto;

import java.util.Objects;

public class UserDTO {
    private int id;
    private String name;
    private String password;
    private String email;
    private boolean admin;

    public UserDTO() {
    }

    public UserDTO(int id, String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.id = id;
    }

    public UserDTO(int id, String name, String password, String email, boolean admin) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.admin = admin;
    }

    public UserDTO(String name, String password, String email, boolean admin) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.admin = admin;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDTO = (UserDTO) o;
        return id == userDTO.id &&
                Objects.equals(name, userDTO.name) &&
                Objects.equals(password, userDTO.password) &&
                Objects.equals(email, userDTO.email);
    }
}
