package nl.han.oose.sapporo.resource.exceptionmapper;

import nl.han.oose.sapporo.persistence.exception.plotDoesNotExistException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class plotDoesNotExistExceptionMapper implements ExceptionMapper<plotDoesNotExistException> {
    @Override
    public Response toResponse(plotDoesNotExistException e) {
        return Response.status(400)
                .entity("This plot does not exist")
                .build();
    }
}
