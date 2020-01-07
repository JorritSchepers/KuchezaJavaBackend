package nl.han.oose.sapporo.resource;

import nl.han.oose.sapporo.dto.UserDTO;
import nl.han.oose.sapporo.service.BuildingService;
import nl.han.oose.sapporo.service.IAccountService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/building")
public class BuildingResource {
    private IAccountService accountService;
    private BuildingService buildingService;

    @Inject
    public void setBuildingService(BuildingService buildingService) {
        this.buildingService = buildingService;
    }

    @Inject
    public void setAccountService(IAccountService accountService) {
        this.accountService = accountService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("sources")
    public Response getAllWaterSources(@QueryParam("token") String token) {
        accountService.verifyToken(token);
        return Response.status(Response.Status.OK)
                .entity(buildingService.getAllWaterSources())
                .build();
    }
}