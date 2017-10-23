package com.grocero.resources;

import com.grocero.common.UserRoles;
import com.grocero.dtos.GroceryListDto;
import com.grocero.exceptions.NoDataFoundException;
import com.grocero.services.GroceryListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

@Component
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@PermitAll
@Scope("prototype")
public class GroceryListResource {

    private UriInfo uriInfo;
    private GroceryListService groceryListService;
    private String customerId;

    public GroceryListResource(String customerId, UriInfo uriInfo) {
        this.uriInfo = uriInfo;
        this.customerId = customerId;
    }

    @Autowired
    public void setGroceryListService(GroceryListService groceryListService) {
        this.groceryListService = groceryListService;
    }

    @POST
    @RolesAllowed({UserRoles.USER})
    public Response create(GroceryListDto groceryListDto) {
        groceryListDto.setCustomerId(customerId);
        groceryListService.create(groceryListDto);
        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        URI uri = builder.path(groceryListDto.getId()).build();
        return Response.created(uri).build();
    }

    @PUT
    @Path("{listId}")
    @RolesAllowed({UserRoles.USER})
    public Response update(@PathParam("listId") String listId,
                           GroceryListDto groceryListDto) {
        groceryListDto.setId(listId);
        groceryListService.update(groceryListDto);
        return Response.ok().build();
    }

    @GET
    @RolesAllowed({UserRoles.ADMIN, UserRoles.USER})
    public Response fetchAll() {
        Response response;
        try {
            response = Response.ok(groceryListService.fetchAll(customerId)).build();
        } catch (NoDataFoundException e) {
            response = Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
        return response;
    }

    @GET
    @Path("{listId}")
    @RolesAllowed({UserRoles.ADMIN, UserRoles.USER})
    public Response getById(@PathParam("listId") String listId) {
        return groceryListService.findById(listId).map(Response::ok).orElse(Response.status(Response.Status.NOT_FOUND)).build();
    }

    @DELETE
    @Path("{listId}")
    @RolesAllowed({UserRoles.ADMIN, UserRoles.USER})
    public Response deleteList(@PathParam("listId") String listId) {
        groceryListService.delete(listId);
        return Response.ok().build();
    }

}
