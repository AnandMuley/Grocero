package com.grocero.resources;


import com.grocero.dtos.CustomerDto;
import com.grocero.dtos.MasterListDto;
import com.grocero.exceptions.NoDataFoundException;
import com.grocero.services.ICustomerService;
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

    private ICustomerService customerService;

    @Autowired
    public CustomerResource(ICustomerService customerService) {
        this.customerService = customerService;
    }

    @POST
    @Path("{id}/masterlist")
    public Response getMasterList(@PathParam("id") String customerId, MasterListDto masterList) {
        masterList.setCustomerId(customerId);
        customerService.save(masterList);
        return Response.ok(masterList).build();
    }

    @POST
    public Response create(CustomerDto customerDto) {
        customerService.save(customerDto);
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