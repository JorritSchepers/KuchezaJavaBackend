package nl.han.oose.sapporo.resource;

import nl.han.oose.sapporo.dto.UserDTO;
import nl.han.oose.sapporo.service.IAccountService;
import nl.han.oose.sapporo.service.IAdminService;
import nl.han.oose.sapporo.service.IPlantService;
import nl.han.oose.sapporo.service.IPlotService;

import javax.inject.Inject;
import javax.ws.rs.*;
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

    @DELETE
    @Path("/{plantIDToDelete}/{plantIDToReplaceWith}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePlant(@QueryParam("token") String token, @PathParam("plantIDToDelete") int plantIDToDelete, @PathParam("plantIDToReplaceWith") int plantIDToReplaceWith) {
        UserDTO user  = accountService.verifyToken(token);
        plantService.deletePlant(user, plantIDToDelete, plantIDToReplaceWith);
        return Response.status(Response.Status.OK)
                .entity(plantService.getAllPlants())
                .build();
    }
}
