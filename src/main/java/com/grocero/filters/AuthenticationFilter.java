package com.grocero.filters;

import com.grocero.dtos.AuthenticationDto;
import com.grocero.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Optional;
import java.util.function.Predicate;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    @Autowired
    private AuthenticationService authenticationService;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String authentication = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authentication == null) {
//            throw new WebApplicationException(Response.Status.FORBIDDEN);
            return;
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
        String authToken = values[1];
        Optional<AuthenticationDto> authenticationDtoOptional = authenticationService.authenticate(username, authToken);
        AuthenticationDto authenticationDto = authenticationDtoOptional
                .orElseThrow(() -> new WebApplicationException("Customer is not registered", Response.Status.NOT_FOUND));

        if (!authenticationDto.isValidToken(authToken)) {
            throw new WebApplicationException("Authentication failed", Response.Status.FORBIDDEN);
        }
        UserPrincipal userPrincipal = new UserPrincipal(
                authenticationDto.getUsername(),
                authenticationDto.getRole(),
                requestContext.getSecurityContext().isSecure()
        );
        AuthorizationContext authorizer = new AuthorizationContext(userPrincipal);
        requestContext.setSecurityContext(authorizer);
    }
}
