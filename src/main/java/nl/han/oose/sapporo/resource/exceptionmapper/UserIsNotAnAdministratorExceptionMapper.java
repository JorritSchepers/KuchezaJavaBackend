package nl.han.oose.sapporo.resource.exceptionmapper;

import nl.han.oose.sapporo.service.exception.UserIsNotAnAdministratorException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UserIsNotAnAdministratorExceptionMapper implements ExceptionMapper<UserIsNotAnAdministratorException> {
    @Override
    public Response toResponse(UserIsNotAnAdministratorException e) {
        int HTTP_STATUS_CODE_FORBIDDEN = 403;
        return Response.status(HTTP_STATUS_CODE_FORBIDDEN)
                .entity("The user is not an administrator.")
                .build();
    }
}
