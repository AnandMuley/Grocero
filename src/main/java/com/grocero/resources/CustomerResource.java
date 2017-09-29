package com.grocero.resources;


import com.grocero.dtos.CustomerDto;
import com.grocero.dtos.MasterListDto;
import com.grocero.dtos.ResponseDto;
import com.grocero.exceptions.CustomerServiceException;
import com.grocero.exceptions.NoDataFoundException;
import com.grocero.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component
@Path("customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerResource {

    private CustomerService customerService;

    @Autowired
    public CustomerResource(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PUT
    @Path("{id}/masterlist")
    public Response createMasterList(@PathParam("id") String customerId, MasterListDto masterList) {
        try {
            masterList.setCustomerId(customerId);
            customerService.createOrUpdate(masterList);
        } catch (CustomerServiceException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(new ResponseDto(e.getMessage())).build();
        }
        return Response.ok(masterList).build();
    }

    @GET
    @Path("{id}/masterlist")
    public Response getMasterList(@PathParam("id") String customerId) {
        try {
            return Response.ok(customerService.getMasterList(customerId)).build();
        } catch (NoDataFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    public Response create(CustomerDto customerDto) {
        customerService.createOrUpdate(customerDto);
        return Response.ok(customerDto).build();
    }

    @GET
    public Response getAll() {
        Response response;
        try {
            response = Response.ok(customerService.getAll()).build();
        } catch (NoDataFoundException e) {
            response = Response.status(Response.Status.NOT_FOUND).build();
        }
        return response;
    }

}