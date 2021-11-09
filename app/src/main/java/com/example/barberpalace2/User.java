package com.example.barberpalace2;

import java.sql.Time;
import java.util.Calendar;

public class User {

    private String name;
    private String email;
    private String password;
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public User(){}
    public User(String name, String email, String password)
    {
        this.name = name;
        this.email = email;
        this.password = password;
        this.type = "Customer";

    }


}
