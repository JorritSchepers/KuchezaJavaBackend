package nl.han.oose.sapporo.resource;

import nl.han.oose.sapporo.dto.UserDTO;
import nl.han.oose.sapporo.service.IAccountService;
import nl.han.oose.sapporo.service.IAdminService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/admin")
public class AdminResource {
    private IAccountService accountService;
    private IAdminService adminService;

    @Inject
    public void setAccountService(IAccountService accountService) {
        this.accountService = accountService;
    }

    @Inject
    public void setAccountService(IAdminService adminService) {
        this.adminService = adminService;
    }

    @GET
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllNonAdminUsers(@QueryParam("token") String token, UserDTO userDTO) {
        UserDTO user  = accountService.verifyToken(token);
        adminService.checkIfUserIsAdmin(user);
        return Response.status(Response.Status.CREATED)
                .entity(accountService.registerUser(userDTO))
                .build();
    }
}

