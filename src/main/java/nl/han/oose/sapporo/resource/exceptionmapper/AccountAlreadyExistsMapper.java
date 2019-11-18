package nl.han.oose.sapporo.resource.exceptionmapper;

import nl.han.oose.sapporo.persistence.exception.AccountAlreadyExistsException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class AccountAlreadyExistsMapper implements ExceptionMapper<AccountAlreadyExistsException> {
    @Override
    public Response toResponse(AccountAlreadyExistsException e) {
        return Response.status(400)
                .entity("There is already an account with the given information.")
                .build();
    }
}