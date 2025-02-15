package nl.han.oose.sapporo.resource;

import nl.han.oose.sapporo.dto.AnimalDTO;
import nl.han.oose.sapporo.dto.PlantDTO;
import nl.han.oose.sapporo.dto.PlotDTO;
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
    public Response harvestPlantFromPlot(@QueryParam("token") String token, @PathParam("id") int plotID, PlotDTO plotDTO) {
        UserDTO user = accountService.verifyToken(token);
        return Response.status(Response.Status.OK)
                .entity(plotService.harvestPlant(plotDTO, user, plotID))
                .build();
    }

    @POST
    @Path("/{id}/purchase")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response purchasePlot(@QueryParam("token") String token, @PathParam("id") int plotID) {
        UserDTO user = accountService.verifyToken(token);
        return Response.status(Response.Status.OK)
                .entity(plotService.purchasePlot(plotID, user))
                .build();
    }

    @POST
    @Path("/{id}/updateAge/{age}")
    public Response updateAge(@QueryParam("token") String token, @PathParam("id") int plotID, @PathParam("age") int age) {
        UserDTO user = accountService.verifyToken(token);
        plotService.updateAge(plotID, age);
        return Response.status(Response.Status.OK).entity("plant").build();
    }

    @POST
    @Path("/{id}/water/{amount}/{removefrominventory}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response editWaterAmountForPlot(@QueryParam("token") String token, @PathParam("id") int plotID, @PathParam("amount") int amount, @PathParam("removefrominventory") boolean ShouldRemoveFromInventory) {
        UserDTO user = accountService.verifyToken(token);
        return Response.status(Response.Status.OK)
                .entity(plotService.editWater(user, plotID, amount, ShouldRemoveFromInventory))
                .build();
    }

    @POST
    @Path("/{id}/status/{status}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response changeStatus(@QueryParam("token") String token, @PathParam("id") int plotID, @PathParam("status") String status) {
        UserDTO user = accountService.verifyToken(token);
        return Response.status(Response.Status.OK)
                .entity(plotService.changeStatus(plotID, status))
                .build();
    }

    @POST
    @Path("/{id}/animal")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response placeAnimalOnPlot(@QueryParam("token") String token, @PathParam("id") int plotID, AnimalDTO animalDTO) {
        UserDTO user = accountService.verifyToken(token);
        return Response.status(Response.Status.OK)
                .entity(plotService.placeAnimal(animalDTO, plotID, user))
                .build();
    }

    @POST
    @Path("/{id}/sellProduct")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response sellAnimalProduct(@QueryParam("token") String token, @PathParam("id") int plotID, PlotDTO plotDTO) {
        UserDTO user = accountService.verifyToken(token);
        return Response.status(Response.Status.OK)
                .entity(plotService.sellProduct(plotDTO, user, plotID))
                .build();
    }

    @POST
    @Path("/{id}/clear")
    public Response clearPlot(@QueryParam("token") String token, @PathParam("id") int plotID) {
        UserDTO user = accountService.verifyToken(token);
        plotService.clearPlot(user, plotID);
        return Response.status(Response.Status.OK)
                .build();
    }
}
