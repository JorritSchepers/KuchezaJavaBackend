package nl.han.oose.sapporo.resource;

import nl.han.oose.sapporo.service.IAccountService;
import nl.han.oose.sapporo.service.IAnimalService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/animal")
public class AnimalResource {
    private IAnimalService animalService;
    private IAccountService accountService;

    @Inject
    public void setAnimalService(IAnimalService animalService) {
        this.animalService = animalService;
    }

    @Inject
    public void setAccountService(IAccountService accountService) {
        this.accountService = accountService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllAnimals(@QueryParam("token") String token) {
        accountService.verifyToken(token);
        return Response.status(Response.Status.OK)
                .entity(animalService.getAllAnimals())
                .build();
    }
}
