package nl.han.oose.sapporo.resource.exceptionmapper;

import nl.han.oose.sapporo.persistence.exception.InsufficientWaterException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InsufficientWaterMapper implements ExceptionMapper<InsufficientWaterException> {

    @Override
    public Response toResponse(InsufficientWaterException e) {
        return Response.status(400)
                .entity("You do not have enough water to perform this action.")
                .build();
    }
}
