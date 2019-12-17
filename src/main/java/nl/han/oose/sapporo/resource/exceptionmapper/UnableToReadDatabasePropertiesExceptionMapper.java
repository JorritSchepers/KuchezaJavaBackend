package nl.han.oose.sapporo.resource.exceptionmapper;

import nl.han.oose.sapporo.persistence.exception.UnableToReadDatabasePropertiesException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UnableToReadDatabasePropertiesExceptionMapper implements ExceptionMapper<UnableToReadDatabasePropertiesException> {
    @Override
    public Response toResponse(UnableToReadDatabasePropertiesException e) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("The back end is unable to read the database properties")
                .build();
    }
}
