package nl.han.oose.sapporo.resource;

import nl.han.oose.sapporo.dto.UserDTO;
import nl.han.oose.sapporo.service.IAccountService;
import nl.han.oose.sapporo.service.IFarmService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/Farm")
public class FarmResource {
    private IFarmService farmService;
    private IAccountService accountService;
    private final int STATUS_CODE_CREATED = 201;

    @Inject
    public void setFarmService(IFarmService farmService) {
        this.farmService = farmService;
    }

    @Inject
    public void setAccountService(IAccountService accountService) {
        this.accountService = accountService;
    }

    @GET
    @Path("/new")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createNewFarm(@QueryParam("token") String token) {
        UserDTO user = accountService.verifyToken(token);
        return Response.status(STATUS_CODE_CREATED)
                .entity(farmService.createFarm(user))
                .build();
    }

    @Produces(MediaType.APPLICATION_JSON)
    public Response getFarm(@QueryParam("token") String token) {
        UserDTO user = accountService.verifyToken(token);
        return Response.status(Response.Status.OK)
                .entity(farmService.getFarm(user))
                .build();
    }
}
