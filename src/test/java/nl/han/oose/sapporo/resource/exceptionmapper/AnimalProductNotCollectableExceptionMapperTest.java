package nl.han.oose.sapporo.resource.exceptionmapper;

import nl.han.oose.sapporo.persistence.exception.AnimalProductNotCollectableException;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AnimalProductNotCollectableExceptionMapperTest {
    private AnimalProductNotCollectableExceptionMapper sut = new AnimalProductNotCollectableExceptionMapper();

    @Test
    void AnimalProductNotCollectableMapperReturnsBadRequestCode() {
        Response response = sut.toResponse(new AnimalProductNotCollectableException());
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }
}

