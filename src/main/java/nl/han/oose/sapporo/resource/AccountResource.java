package nl.han.oose.sapporo.resource;

import nl.han.oose.sapporo.dto.LoginDTO;
import nl.han.oose.sapporo.dto.UserDTO;
import nl.han.oose.sapporo.service.IAccountService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/account")
public class AccountResource {
    private IAccountService accountService;

    @Inject
    public void setAccountService(IAccountService accountService) {
        this.accountService = accountService;
    }


    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerUser(UserDTO userDTO) {
        return Response.status(Response.Status.CREATED)
                .entity(accountService.registerUser(userDTO))
                .build();
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(LoginDTO loginDTO) {
        return Response.status(Response.Status.OK)
                .entity(accountService.loginUser(loginDTO))
                .build();
    }

    @POST
    @Path("/logout/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response logout(@QueryParam("token") String token) {
        accountService.logoutUser(token);
        return Response.status(Response.Status.OK).build();
    }
}