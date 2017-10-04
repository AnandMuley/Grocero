package com.grocero.resources;

import com.grocero.dtos.CustomerDto;
import org.springframework.stereotype.Component;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.security.Principal;
import java.util.UUID;

@Component
@Path("admin")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AdminResource {

    @GET
    @RolesAllowed({"dummy"})
    public Response getAll(@Context SecurityContext securityContext) {
        Principal principal = securityContext.getUserPrincipal();
        return Response.ok(new CustomerDto(UUID.randomUUID().toString(), principal.getName())).build();
    }

}
