package com.grocero.resources;

import com.grocero.dtos.GroceryListDto;
import com.grocero.services.GroceryListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;

@Component
@Path("grocerylists")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class GroceriesListResource {

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
		List<GroceryListDto> groceryLists = groceryListService.fetchAll();
		return Response.ok(groceryLists).build();
	}

	@GET
	@Path("{listId}")
	public Response getById(@PathParam("listId") String listId) {
		GroceryListDto groceryListDto = groceryListService.findById(listId);
		return Response.ok(groceryListDto).build();
	}

	@DELETE
	@Path("{listId}")
	public Response deleteList(@PathParam("listId") String listId) {
		groceryListService.delete(listId);
		return Response.ok().build();
	}

}
