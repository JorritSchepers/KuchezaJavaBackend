package nl.han.oose.sapporo.resource;

import nl.han.oose.sapporo.dto.UserDTO;
import nl.han.oose.sapporo.service.IAccountService;
import nl.han.oose.sapporo.service.IAdminService;
import nl.han.oose.sapporo.service.IAnimalService;
import nl.han.oose.sapporo.service.IPlotService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/animal")
public class AnimalResource {
    private IAnimalService animalService;
    private IAccountService accountService;
    private IAdminService adminService;
    private IPlotService plotService;

    @Inject
    public void setAccountService(IAccountService accountService) {
        this.accountService = accountService;
    }

    @Inject
    public void setAdminService(IAdminService adminService) {
        this.adminService = adminService;
    }

    @Inject
    public void setPlotService(IPlotService plotService) {
        this.plotService = plotService;
    }

    @Inject
    public void setAnimalService(IAnimalService animalService) {
        this.animalService = animalService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllAnimals(@QueryParam("token") String token) {
        accountService.verifyToken(token);
        return Response.status(Response.Status.OK)
                .entity(animalService.getAllAnimals())
                .build();
    }

    @DELETE
    @Path("/{animalIDToDelete}/{animalIDToReplaceWith}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteAnimal(@QueryParam("token") String token, @PathParam("animalIDToDelete") int animalIDToDelete, @PathParam("animalIDToReplaceWith") int animalIDToReplaceWith) {
        UserDTO user = accountService.verifyToken(token);
        adminService.checkIfUserIsAdmin(user);
        plotService.replaceAnimalsOnAllPlots(animalIDToDelete, animalIDToReplaceWith);
        animalService.deleteAnimal(animalIDToDelete);
        return Response.status(Response.Status.OK)
                .entity(animalService.getAllAnimals())
                .build();
    }
}
