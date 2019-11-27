package nl.han.oose.sapporo.dto;

import java.util.Objects;

public class UserDTO {
    private int ID;
    private String name;
    private String password;
    private String email;

    public UserDTO() { }

    public UserDTO(int ID, String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.ID = ID;
    }

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

    public int getID() {
        return ID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDTO = (UserDTO) o;
        return ID == userDTO.ID &&
                Objects.equals(name, userDTO.name) &&
                Objects.equals(password, userDTO.password) &&
                Objects.equals(email, userDTO.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, name, password, email);
    }
}
