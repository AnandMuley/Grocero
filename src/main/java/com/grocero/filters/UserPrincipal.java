package com.grocero.filters;

import java.security.Principal;

public class UserPrincipal implements Principal {

    private String username;
    private String role;
    private boolean isSecured;

    public UserPrincipal(String username, String role, boolean isSecured) {
        this.username = username;
        this.role = role;
        this.isSecured = isSecured;
    }

    public String getRole() {
        return role;
    }

    public boolean isSecured() {
        return isSecured;
    }

    @Override
    public String getName() {
        return username;
    }
}
