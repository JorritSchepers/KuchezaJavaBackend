package nl.han.oose.sapporo.resource.exceptionmapper;

import nl.han.oose.sapporo.persistence.exception.PlotDoesNotExistException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class PlotDoesNotExistExceptionMapper implements ExceptionMapper<PlotDoesNotExistException> {

    @Override
    public Response toResponse(PlotDoesNotExistException e) {
        return Response.status(400)
                .entity("This plot does not exist")
                .build();
    }
}