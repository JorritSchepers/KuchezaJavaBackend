package nl.han.oose.sapporo.resource.exceptionmapper;

import nl.han.oose.sapporo.persistence.exception.InvalidLoginInformationException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InvalidLoginInformationExceptionMapper implements ExceptionMapper<InvalidLoginInformationException> {
    @Override
    public Response toResponse(InvalidLoginInformationException e) {
        return Response.status(400)
                .entity("The username or password was incorrect")
                .build();
    }
}
