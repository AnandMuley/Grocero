package com.grocero.filters;

import com.grocero.beans.CustomerBean;
import com.grocero.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.nio.charset.Charset;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String authentication = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authentication == null) {
            throw new WebApplicationException(Response.Status.FORBIDDEN);
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
        CustomerBean customerBean = customerRepository.findOneByUsernameAndPassword(username, password);
        if (customerBean == null) {
            throw new WebApplicationException("Customer is not registered", Response.Status.NOT_FOUND);
        }

        if (!customerBean.getPassword().equals(password)) {
            throw new WebApplicationException("Authentication failed", Response.Status.FORBIDDEN);
        }
        UserPrincipal userPrincipal = new UserPrincipal(
                customerBean.getUsername(),
                customerBean.getRole(),
                requestContext.getSecurityContext().isSecure()
        );
        AuthorizationContext authorizer = new AuthorizationContext(userPrincipal);
        requestContext.setSecurityContext(authorizer);
    }
}
