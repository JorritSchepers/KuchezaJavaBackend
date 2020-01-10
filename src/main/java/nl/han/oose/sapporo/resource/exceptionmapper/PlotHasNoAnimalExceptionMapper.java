package nl.han.oose.sapporo.resource.exceptionmapper;

import nl.han.oose.sapporo.persistence.exception.PlotHasNoAnimalException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class PlotHasNoAnimalExceptionMapper implements ExceptionMapper<PlotHasNoAnimalException> {

    @Override
    public Response toResponse(PlotHasNoAnimalException e) {
        return Response.status(400)
                .entity("The plot has no animal")
                .build();
    }
}
