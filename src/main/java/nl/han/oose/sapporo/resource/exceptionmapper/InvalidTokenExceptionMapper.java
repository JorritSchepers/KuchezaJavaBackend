package nl.han.oose.sapporo.resource.exceptionmapper;

import nl.han.oose.sapporo.persistence.exception.InvalidTokenException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InvalidTokenExceptionMapper implements ExceptionMapper<InvalidTokenException> {
    @Override
    public Response toResponse(InvalidTokenException e) {
        return Response.status(401)
                .entity("The token was invalid")
                .build();
    }
}
