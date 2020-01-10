package nl.han.oose.sapporo.resource.exceptionmapper;

import nl.han.oose.sapporo.persistence.exception.UserAlreadyHasFarmException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UserAlreadyHasFarmExceptionMapper implements ExceptionMapper<UserAlreadyHasFarmException> {

    @Override
    public Response toResponse(UserAlreadyHasFarmException e) {
        return Response.status(400)
                .entity("This user already has a farm")
                .build();
    }
}
