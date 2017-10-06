package com.grocero.resources;

import com.grocero.common.UserRoles;
import com.grocero.dtos.ProductDto;
import com.grocero.exceptions.NoDataFoundException;
import com.grocero.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Component
@Path("products")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@PermitAll
public class ProductResource {

    private ProductService productService;

    @Autowired
    public ProductResource(ProductService productService) {
        this.productService = productService;
    }

    @POST
    @RolesAllowed(UserRoles.ADMIN)
    public Response add(ProductDto productDto) {
        productService.add(productDto);
        return Response.ok(productDto).build();
    }

    @GET
    @RolesAllowed({UserRoles.ADMIN,UserRoles.USER})
    public Response getAll() {
        Response response;
        try {
            List<ProductDto> productDtos = productService.getAll();
            response = Response.ok(productDtos).build();
        } catch (NoDataFoundException e) {
            response = Response.noContent().build();
        }
        return response;
    }

    @PUT
    @Path("{productId}")
    @RolesAllowed({UserRoles.ADMIN})
    public Response updateProduct(@PathParam("productId") String productId,
                                  ProductDto productDto) {
        productDto.setId(productId);
        productService.update(productDto);
        return Response.ok().build();
    }

    @GET
    @Path("{name}")
    @RolesAllowed({UserRoles.ADMIN,UserRoles.USER})
    public Response getByName(@PathParam("name") String name) {
        ProductDto productDto = productService.findByName(name);
        return Response.ok(productDto).build();
    }
}
