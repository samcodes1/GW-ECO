package com.rtechnologies.echofriend.models;

public class LoginRequest {
    private String usernameOrEmail;
    private String password;
    // Constructors, getters, and setters

    public String getUsernameOrEmail() {
        return usernameOrEmail;
    }

    public void setUsernameOrEmail(String usernameOrEmail) {
        this.usernameOrEmail = usernameOrEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}

