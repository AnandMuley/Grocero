package com.grocero.resources;

import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.grocero.dtos.GroceryListDto;
import com.grocero.services.GroceryListService;

@Component
@Path("grocerylists")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ListResource {

	@Context
	UriInfo uriInfo;

	@Autowired
	private GroceryListService groceryListService;

	@POST
	public Response create(GroceryListDto groceryListDto) {
		groceryListService.create(groceryListDto);
		UriBuilder builder = uriInfo.getAbsolutePathBuilder();
		URI uri = builder.path(groceryListDto.getId()).build();
		return Response.created(uri).build();
	}

	@GET
	@Path("{listId}")
	public Response getById(@PathParam("listId") String listId) {
		GroceryListDto groceryListDto = groceryListService.findById(listId);
		return Response.ok(groceryListDto).build();
	}

}
