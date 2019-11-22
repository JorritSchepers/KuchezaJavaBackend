package nl.han.oose.sapporo.resource;

import nl.han.oose.sapporo.service.IAccountService;
import nl.han.oose.sapporo.service.IPlantService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/plant")
public class PlantResource {
    private IPlantService plantService;
    private IAccountService accountService;

    @Inject
    public void setPlantService(IPlantService plantService) {
        this.plantService = plantService;
    }

    @Inject
    public void setAccountService(IAccountService accountService) {
        this.accountService = accountService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPlants(@QueryParam("token") String token) {
        accountService.verifyToken(token);
        return Response.status(Response.Status.OK)
                .entity(plantService.getAllPlants())
                .build();
    }
}