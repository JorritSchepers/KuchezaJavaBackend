package nl.han.oose.sapporo.resource;

import nl.han.oose.sapporo.dto.PlantDTO;
import nl.han.oose.sapporo.dto.UserDTO;
import nl.han.oose.sapporo.service.IAccountService;
import nl.han.oose.sapporo.service.IPlotService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/plot")
public class PlotResource {
    private IAccountService accountService;
    private IPlotService plotService;

    @Inject
    public void setAccountService(IAccountService accountService) {
        this.accountService = accountService;
    }

    @Inject
    public void setPlotService(IPlotService plotService) {
        this.plotService = plotService;
    }

    @POST
    @Path("/{id}/plant")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response placePlantOnPlot(@QueryParam("token") String token, @PathParam("id") int plotID, PlantDTO plantDTO) {
        UserDTO user = accountService.verifyToken(token);
        return Response.status(Response.Status.OK)
                .entity(plotService.placePlant(plantDTO, plotID, user))
                .build();
    }

    @POST
    @Path("/{id}/harvest")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response harvestPlantFromPlot(@QueryParam("token") String token, @PathParam("id") int plotID, PlantDTO plantDTO) {
        UserDTO user = accountService.verifyToken(token);
        return Response.status(Response.Status.OK)
                .entity(plotService.harvesPlant(plantDTO, user, plotID))
                .build();
    }
}