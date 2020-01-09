package nl.han.oose.sapporo.resource;

import nl.han.oose.sapporo.service.IBuildingService;
import nl.han.oose.sapporo.service.IAccountService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/building")
public class BuildingResource {
    private IAccountService accountService;
    private IBuildingService IBuildingService;

    @Inject
    public void setIBuildingService(IBuildingService IBuildingService) {
        this.IBuildingService = IBuildingService;
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
                .entity(IBuildingService.getAllWaterSources())
                .build();
    }
}