package com.grocero.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.grocero.beans.ProductBean;
import com.grocero.services.ProductService;
import com.grocero.utils.MeasurementUnit;

@Component
@Path("products")
@Consumes(MediaType.APPLICATION_JSON)
public class ProductResource {

	@Autowired
	private ProductService productService;

	@POST
	@Path("add")
	@Produces(MediaType.APPLICATION_JSON)
	public Response add(ProductBean productBean) {
		productService.add(productBean);
		return Response.ok(productBean).build();
	}

	@GET
	@Path("list")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listAll() {
		List<ProductBean> productBeans = new ArrayList<ProductBean>();
		productBeans.add(new ProductBean("101", "Oil", MeasurementUnit.Litre));
		productBeans.add(new ProductBean("102", "Sugar",
				MeasurementUnit.Kilograms));
		return Response.ok(productBeans).build();
	}

	@GET
	@Path("find")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findByName(@QueryParam("name") String name) {
		ProductBean productBean = productService.findByName(name);
		return Response.ok(productBean).build();
	}
}
