package nl.han.oose.sapporo.resource.exceptionmapper;

import nl.han.oose.sapporo.persistence.exception.PlotHasNoWaterSourceException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class PlotHasNoWaterSourceExceptionMapper implements ExceptionMapper<PlotHasNoWaterSourceException> {

    @Override
    public Response toResponse(PlotHasNoWaterSourceException e) {
        return Response.status(400)
                .entity("The plot has no watersource")
                .build();
    }
}
