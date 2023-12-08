package com.example.filmartstore.auth;

public class AccountRequest {
    private String email;
    private String password;

    public AccountRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public AccountRequest() {
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
