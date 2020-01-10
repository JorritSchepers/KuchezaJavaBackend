package nl.han.oose.sapporo.resource.exceptionmapper;

import nl.han.oose.sapporo.service.exception.UserIsNotAnAdministratorException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UserIsNotAnAdministratorExceptionMapper implements ExceptionMapper<UserIsNotAnAdministratorException> {
    @Override
    public Response toResponse(UserIsNotAnAdministratorException e) {
        return Response.status(Response.Status.FORBIDDEN)
                .entity("The user is not an administrator.")
                .build();
    }
}
