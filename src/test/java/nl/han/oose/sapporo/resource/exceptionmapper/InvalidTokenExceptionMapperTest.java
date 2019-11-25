package nl.han.oose.sapporo.resource.exceptionmapper;

import nl.han.oose.sapporo.persistence.exception.InvalidTokenException;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InvalidTokenExceptionMapperTest {
    private InvalidTokenExceptionMapper sut = new InvalidTokenExceptionMapper();

    @Test
    void PersistenceMapperReturnsBadRequestCode() {
        Response response = sut.toResponse(new InvalidTokenException());
        assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
    }
}

