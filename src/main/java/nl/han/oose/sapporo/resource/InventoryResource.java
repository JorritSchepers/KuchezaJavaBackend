package nl.han.oose.sapporo.resource;

import nl.han.oose.sapporo.dto.UserDTO;
import nl.han.oose.sapporo.service.IAccountService;
import nl.han.oose.sapporo.service.IInventoryService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/inventory")
public class InventoryResource {
    private IAccountService accountService;
    private IInventoryService inventoryService;

    @Inject
    public void setAccountService(IAccountService accountService) {
        this.accountService = accountService;
    }

    @Inject
    public void setInventoryService(IInventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getInventory(@QueryParam("token") String token) {
        UserDTO user  = accountService.verifyToken(token);
        return Response.status(Response.Status.OK)
                .entity(inventoryService.getInventory(user))
                .build();
    }
}