package nl.han.oose.sapporo.dto;

import java.util.ArrayList;

public class AllUsersDTO {
    private ArrayList<UserDTO> users;

    public AllUsersDTO() {
        users = new ArrayList<>();
    }

    public void addUser(UserDTO user) {
        this.users.add(user);
    }

    public ArrayList<UserDTO> getUsers() {
        return users;
    }
}
