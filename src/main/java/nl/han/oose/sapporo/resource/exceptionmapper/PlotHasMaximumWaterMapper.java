package nl.han.oose.sapporo.resource.exceptionmapper;

import nl.han.oose.sapporo.persistence.exception.PlotHasMaximumWaterException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class PlotHasMaximumWaterMapper implements ExceptionMapper<PlotHasMaximumWaterException> {

    @Override
    public Response toResponse(PlotHasMaximumWaterException e) {
        return Response.status(400)
                .entity("Plot has been filled with water to the maximum already!")
                .build();
    }
}
