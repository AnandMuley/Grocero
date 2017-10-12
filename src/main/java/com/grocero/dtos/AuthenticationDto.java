package com.grocero.dtos;

import org.springframework.stereotype.Component;

@Component
public class AuthenticationDto {

    protected String username;
    protected String role;
    protected String authToken;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public boolean isValidToken(String authToken){
        return authToken.equals(this.authToken);
    }

    @Override
    public String toString() {
        return "AuthenticationDto{" +
                "username='" + username + '\'' +
                ", role='" + role + '\'' +
                ", authToken='" + authToken + '\'' +
                '}';
    }
}
