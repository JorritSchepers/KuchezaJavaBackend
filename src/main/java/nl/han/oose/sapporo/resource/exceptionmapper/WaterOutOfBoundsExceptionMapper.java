package nl.han.oose.sapporo.resource.exceptionmapper;

import nl.han.oose.sapporo.persistence.exception.WaterOutOfBoundsException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class WaterOutOfBoundsExceptionMapper implements ExceptionMapper<WaterOutOfBoundsException> {

    @Override
    public Response toResponse(WaterOutOfBoundsException e) {
        return Response.status(400)
                .entity("The water is already at its minimum or maximum amount!")
                .build();
    }
}
