package nl.han.oose.sapporo.resource.exceptionmapper;

import nl.han.oose.sapporo.persistence.exception.WaterOutOfBoundsException;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WaterOutOfBoundsExceptionMapperTest {
    private WaterOutOfBoundsExceptionMapper sut = new WaterOutOfBoundsExceptionMapper();

    @Test
    void PersistenceMapperReturnsBadRequestCode() {
        Response response = sut.toResponse(new WaterOutOfBoundsException());
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }
}
