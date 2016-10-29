package com.grocero.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;

import com.grocero.dtos.ProductDto;

@Component
@Path("customer")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CustomerResource {

	@POST
	public Response add(ProductDto productDto) {
		System.out.println("ProductDto is successfully validated");
		return Response.ok(productDto).build();
	}

}