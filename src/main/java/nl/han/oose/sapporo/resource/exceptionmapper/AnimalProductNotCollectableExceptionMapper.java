package nl.han.oose.sapporo.resource.exceptionmapper;

import nl.han.oose.sapporo.persistence.exception.AnimalProductNotCollectableException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class AnimalProductNotCollectableExceptionMapper implements ExceptionMapper<AnimalProductNotCollectableException> {

    @Override
    public Response toResponse(AnimalProductNotCollectableException e) {
        return Response.status(400)
                .entity("The product is not ready, you cannot sell it yet.")
                .build();
    }
}

