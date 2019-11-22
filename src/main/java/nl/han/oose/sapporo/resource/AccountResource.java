package nl.han.oose.sapporo.resource;

import nl.han.oose.sapporo.dto.UserDTO;
import nl.han.oose.sapporo.service.IAccountService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/account")
public class AccountResource {
    private IAccountService accountService;

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerUser(UserDTO userDTO) {
        return Response.status(Response.Status.CREATED)
                .entity(accountService.registerUser(userDTO))
                .build();
    }

    @Inject
    public void setAccountService(IAccountService accountService) {
        this.accountService = accountService;
    }
}