package nl.han.oose.sapporo.resource.exceptionmapper;

import nl.han.oose.sapporo.persistence.exception.PersistenceException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class PersistenceMapper implements ExceptionMapper<PersistenceException> {

    @Override
    public Response toResponse(PersistenceException e) {
        return Response.status(400)
                .entity("There was a problem with the database, please try again.")
                .build();
    }
}