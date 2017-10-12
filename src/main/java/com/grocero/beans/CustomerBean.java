package com.grocero.beans;

import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Scope("prototype")
@Component
@Document(collection = "customers")
public class CustomerBean extends AbstractAuthenticationBean {

    private String id;
    private String name;
    private String password;
    private LocalDateTime authTokenValidTill;

    public CustomerBean() {
    }

    public CustomerBean(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public LocalDateTime getAuthTokenValidTill() {
        return authTokenValidTill;
    }

    public void setAuthTokenValidTill(LocalDateTime authTokenValidTill) {
        this.authTokenValidTill = authTokenValidTill;
    }

    @Override
    public String toString() {
        return "CustomerBean{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", authToken='" + authToken + '\'' +
                ", authTokenValidTill=" + authTokenValidTill +
                '}';
    }
}
