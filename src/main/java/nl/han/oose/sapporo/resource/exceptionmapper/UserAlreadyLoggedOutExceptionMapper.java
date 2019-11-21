package nl.han.oose.sapporo.resource.exceptionmapper;

import nl.han.oose.sapporo.persistence.exception.AccountAlreadyExistsException;
import nl.han.oose.sapporo.service.exception.UserAlreadyLoggedOutException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UserAlreadyLoggedOutExceptionMapper implements ExceptionMapper<UserAlreadyLoggedOutException> {
    @Override
    public Response toResponse(UserAlreadyLoggedOutException e) {
        return Response.status(400)
                .entity("The user is already logged out")
                .build();
    }
}