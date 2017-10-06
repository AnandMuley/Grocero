package com.grocero.filters;

import javax.ws.rs.core.SecurityContext;
import java.security.Principal;

public class AuthorizationContext implements SecurityContext {

    private final UserPrincipal userPrincipal;

    public AuthorizationContext(UserPrincipal userPrincipal) {
        this.userPrincipal = userPrincipal;
    }

    @Override
    public Principal getUserPrincipal() {
        return userPrincipal;
    }

    @Override
    public boolean isUserInRole(String role) {
        return userPrincipal.getRole().equals(role);
    }

    @Override
    public boolean isSecure() {
        return userPrincipal.isSecured();
    }

    @Override
    public String getAuthenticationScheme() {
        return BASIC_AUTH;
    }
}
