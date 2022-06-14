package com.devlab.prodcatalog.backend.dto;

public class UserAuthenticationDto {

    private String email;
    private String password;

    public UserAuthenticationDto() {
    }

    public UserAuthenticationDto(String username, String password) {
        this.email = username;
        this.password = password;
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
}
