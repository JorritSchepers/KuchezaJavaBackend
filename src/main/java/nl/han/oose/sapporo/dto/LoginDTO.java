package nl.han.oose.sapporo.dto;

import com.mysql.cj.log.Log;

public class LoginDTO {
    private String name;
    private String password;

    public LoginDTO() {

    }

    public LoginDTO(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
