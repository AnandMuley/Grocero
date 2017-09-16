package com.grocero.resources;


import com.grocero.dtos.CustomerDto;
import com.grocero.dtos.MasterListDto;
import com.grocero.services.CustomerService;
import com.grocero.services.ModifyingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component
@Path("customers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CustomerResource {

    private CustomerService customerService;

    @Autowired
    public CustomerResource(CustomerService customerService) {
        this.customerService = customerService;
    }

    @POST
    @Path("{id}/masterlist")
    public Response getMasterList(@PathParam("id") String customerId, MasterListDto masterList) {
        masterList.setCustomerId(customerId);
        return Response.ok(masterList).build();
    }

    @POST
    public Response create(CustomerDto customerDto) {
        customerService.save(customerDto);
        return Response.ok(customerDto).build();
    }

    @GET
    public Response getAll() {
        return Response.ok("All customers").build();
    }
}
