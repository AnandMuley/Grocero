package com.grocero.resources;


import com.grocero.dtos.CustomerDto;
import com.grocero.dtos.MasterListDto;
import com.grocero.services.ModifyingService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("customers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CustomerResource {

    @Autowired
    private ModifyingService modifyingService;

    @POST
    @Path("{id}/masterlist")
    public Response getMasterList(@PathParam("id") String customerId, MasterListDto masterList) {
        masterList.setCustomerId(customerId);
        return Response.ok(masterList).build();
    }

    @POST
    public Response create(CustomerDto customerDto) {
        modifyingService.save(customerDto);
        return Response.ok(customerDto).build();
    }
}
