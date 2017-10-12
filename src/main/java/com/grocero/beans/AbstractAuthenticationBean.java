package com.grocero.beans;

public abstract class AbstractAuthenticationBean {

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

    @Override
    public String toString() {
        return "AbstractAuthenticationBean{" +
                "username='" + username + '\'' +
                ", role='" + role + '\'' +
                ", authToken='" + authToken + '\'' +
                '}';
    }
}
