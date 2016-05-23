package com.grocero.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.grocero.beans.ProductBean;
import com.grocero.dtos.ProductDto;
import com.grocero.services.ProductService;

@Component
@Path("products")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProductResource {

	@Autowired
	private ProductService productService;

	@POST
	public Response add(ProductDto productDto) {
		productService.add(productDto);
		return Response.ok(productDto).build();
	}

	@GET
	@Path("list")
	public Response getAll() {
		List<ProductDto> productDtos = new ArrayList<ProductDto>();
		productDtos.add(new ProductDto("101", "Oil", "Litre"));
		productDtos.add(new ProductDto("102", "Sugar", "Kilograms"));
		return Response.ok(productDtos).build();
	}

	@GET
	@Path("{name}")
	public Response getByName(@PathParam("name") String name) {
		ProductBean productBean = productService.findByName(name);
		return Response.ok(productBean).build();
	}
}
