package nl.han.oose.sapporo.resource.exceptionmapper;

import nl.han.oose.sapporo.persistence.exception.PlotHasNotPlantException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class PlotHasNotPlantExceptionMapper implements ExceptionMapper<PlotHasNotPlantException> {

    @Override
    public Response toResponse(PlotHasNotPlantException e) {
        return Response.status(400)
                .entity("The plot has no plant")
                .build();
    }
}
