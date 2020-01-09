package nl.han.oose.sapporo.resource.exceptionmapper;

import nl.han.oose.sapporo.persistence.exception.PlantNotGrownException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class PlantNotGrownExceptionMapper implements ExceptionMapper<PlantNotGrownException> {

    @Override
    public Response toResponse(PlantNotGrownException e) {
        return Response.status(400)
                .entity("The plant is not grown, you cannot sell it yet.")
                .build();
    }
}

