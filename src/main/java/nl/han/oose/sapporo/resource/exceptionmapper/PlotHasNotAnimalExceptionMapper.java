package nl.han.oose.sapporo.resource.exceptionmapper;

import nl.han.oose.sapporo.persistence.exception.PlotHasNotAnimalException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class PlotHasNotAnimalExceptionMapper implements ExceptionMapper<PlotHasNotAnimalException> {

    @Override
    public Response toResponse(PlotHasNotAnimalException e) {
        return Response.status(400)
                .entity("The plot has no animal")
                .build();
    }
}
