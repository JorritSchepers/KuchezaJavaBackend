package nl.han.oose.sapporo.resource;

import nl.han.oose.sapporo.service.IAccountService;
import nl.han.oose.sapporo.service.IBuildingService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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