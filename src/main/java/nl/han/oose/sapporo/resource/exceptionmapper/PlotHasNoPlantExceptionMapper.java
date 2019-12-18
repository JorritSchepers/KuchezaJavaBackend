package nl.han.oose.sapporo.resource.exceptionmapper;

import nl.han.oose.sapporo.persistence.exception.PlotHasNoPlantException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class PlotHasNoPlantExceptionMapper implements ExceptionMapper<PlotHasNoPlantException> {

    @Override
    public Response toResponse(PlotHasNoPlantException e) {
        return Response.status(400)
                .entity("The plot has no plant")
                .build();
    }
}
