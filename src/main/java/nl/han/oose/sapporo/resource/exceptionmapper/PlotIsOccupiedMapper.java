package nl.han.oose.sapporo.resource.exceptionmapper;

import nl.han.oose.sapporo.persistence.exception.PlotIsOccupiedException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class PlotIsOccupiedMapper implements ExceptionMapper<PlotIsOccupiedException> {

    @Override
    public Response toResponse(PlotIsOccupiedException e) {
        return Response.status(400)
                .entity("The plot is already occupied, you cannot place a second item.")
                .build();
    }
}
