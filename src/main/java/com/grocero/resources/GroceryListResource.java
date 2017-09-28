package com.grocero.resources;

import com.grocero.dtos.GroceryListDto;
import com.grocero.exceptions.NoDataFoundException;
import com.grocero.services.GroceryListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@Component
@Path("grocerylists")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class GroceryListResource {

    @Context
    UriInfo uriInfo;

    private GroceryListService groceryListService;

    @Autowired
    public GroceryListResource(GroceryListService groceryListService) {
        this.groceryListService = groceryListService;
    }

    @POST
    public Response create(GroceryListDto groceryListDto) {
        groceryListService.create(groceryListDto);
        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        URI uri = builder.path(groceryListDto.getId()).build();
        return Response.created(uri).build();
    }

    @PUT
    @Path("{listId}")
    public Response update(@PathParam("listId") String listId,
                           GroceryListDto groceryListDto) {
        groceryListDto.setId(listId);
        groceryListService.update(groceryListDto);
        return Response.ok().build();
    }

    @GET
    public Response fetchAll() {
        Response response;
        try {
            response = Response.ok(groceryListService.fetchAll()).build();
        } catch (NoDataFoundException e) {
            response = Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
        return response;
    }

    @GET
    @Path("{listId}")
    public Response getById(@PathParam("listId") String listId) {
        return groceryListService.findById(listId).map(Response::ok).orElse(Response.status(Response.Status.NOT_FOUND)).build();
    }

    @DELETE
    @Path("{listId}")
    public Response deleteList(@PathParam("listId") String listId) {
        groceryListService.delete(listId);
        return Response.ok().build();
    }

}
