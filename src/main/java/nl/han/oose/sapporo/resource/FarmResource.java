package nl.han.oose.sapporo.resource;

import nl.han.oose.sapporo.dto.FarmDTO;
import nl.han.oose.sapporo.dto.UserDTO;
import nl.han.oose.sapporo.service.IAccountService;
import nl.han.oose.sapporo.service.IFarmService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/Farm")
public class FarmResource {
    private IFarmService farmService;
    private IAccountService accountService;
    private final int STATUS_CODE_CREATED = 201;

    @Inject
    public void setFarmService(IFarmService farmService){
        this.farmService = farmService;
    }

    @Inject
    public void setAccountService(IAccountService accountService){
        this.accountService = accountService;
    }

    @GET
    @Path("/new")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addNewFarm(@QueryParam("token") String token){
        UserDTO user = new UserDTO();
        return Response.status(STATUS_CODE_CREATED)
                .entity(farmService.addFarm(user))
                .build();
    }


}
