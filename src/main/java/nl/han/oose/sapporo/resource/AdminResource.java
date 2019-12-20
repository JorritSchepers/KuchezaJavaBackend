package nl.han.oose.sapporo.resource;

import nl.han.oose.sapporo.dto.UserDTO;
import nl.han.oose.sapporo.service.IAccountService;
import nl.han.oose.sapporo.service.IActionService;
import nl.han.oose.sapporo.service.IAdminService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/admin")
public class AdminResource {
    private IAccountService accountService;
    private IAdminService adminService;
    private IActionService actionService;

    @Inject
    public void setAccountService(IAccountService accountService) {
        this.accountService = accountService;
    }

    @Inject
    public void setAdminService(IAdminService adminService) {
        this.adminService = adminService;
    }

    @Inject
    public void setActionService(IActionService actionService) {
        this.actionService = actionService;
    }

    @GET
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllNonAdminUsers(@QueryParam("token") String token) {
        UserDTO user = accountService.verifyToken(token);
        return Response.status(Response.Status.OK)
                .entity(adminService.getAllNonAdminUsers(user))
                .build();
    }

    @DELETE
    @Path("/user/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUser(@QueryParam("token") String token, @PathParam("id") int userID) {
        UserDTO user = accountService.verifyToken(token);
        adminService.deleteUser(user, userID);
        return Response.status(Response.Status.OK)
                .entity(adminService.getAllNonAdminUsers(user))
                .build();
    }

    @GET
    @Path("/actions/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserStatistics(@QueryParam("token") String token, @PathParam("id") int userID) {
        UserDTO user = accountService.verifyToken(token);
        adminService.checkIfUserIsAdmin(user);
        return Response.status(Response.Status.OK)
                .entity(actionService.getUserActions(userID))
                .build();
    }
}

