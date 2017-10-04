package com.grocero.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.Principal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class SecurityFilter implements ContainerRequestFilter {

    @Autowired
    private UserStore userStore;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String authentication = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authentication == null) {
            throw new RuntimeException("Unauthorized");
        }
        if (!authentication.startsWith("Basic ")) {
            return;
        }
        authentication = authentication.substring("Basic ".length());
        String[] values = new String(DatatypeConverter.parseBase64Binary(authentication), Charset.forName("ASCII")).split(":");
        if (values.length < 2) {
            throw new WebApplicationException(400);
        }
        String username = values[0];
        String password = values[1];
        User user = userStore.getUser(username);
        if (user == null) {
            throw new RuntimeException("Authentication required");
        }

        if (!user.password.equals(password)) {
            throw new RuntimeException("Password Authentication required");
        }

        Authorizer authorizer = new Authorizer(user);
        requestContext.setSecurityContext(authorizer);
    }

    public static class Authorizer implements SecurityContext {

        private final User user;

        public Authorizer(User user) {
            this.user = user;
        }

        @Override
        public Principal getUserPrincipal() {
            return this.user;
        }

        @Override
        public boolean isUserInRole(String role) {
            return this.user.role.equals(role);
        }

        @Override
        public boolean isSecure() {
            return true;
        }

        @Override
        public String getAuthenticationScheme() {
            return "Basic";
        }

    }

    public static class User implements Principal {

        String username;
        String password;
        String role;

        public User(String username) {
            this.username = username;
        }

        public User(String username, String password, String role) {
            this.username = username;
            this.role = role;
            this.password = password;
        }

        @Override
        public String getName() {
            return username;
        }

    }

    @Component
    public static class UserStore {
        public final Map<String, User> users = new ConcurrentHashMap<>();

        public UserStore() {
            users.put("dummy", new User("dummy", "dummy", "DUMMY"));
            users.put("admin", new User("admin", "admin", "ADMIN"));
        }

        public User getUser(String username) {
            return users.get(username);
        }
    }
}
