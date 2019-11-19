package nl.han.oose.sapporo.resource;

import nl.han.oose.sapporo.dto.TokenDTO;
import nl.han.oose.sapporo.service.IAccountService;
import nl.han.oose.sapporo.service.IPlantService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/plant")
public class PlantResource {
    private IPlantService plantService;
    private IAccountService accountService;
    private final int STATUS_CODE_OK = 200;

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
    public Response getAllPlants(String token) {
        accountService.authenticateByToken(token);
        return Response.status(STATUS_CODE_OK)
                .entity(plantService.getAllPlants())
                .build();
    }
}