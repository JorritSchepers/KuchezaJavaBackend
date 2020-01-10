package nl.han.oose.sapporo.resource.exceptionmapper;

import nl.han.oose.sapporo.persistence.exception.PlotDoesNotExistException;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlotDoesNotExistExceptionMapperTest {
    private PlotDoesNotExistExceptionMapper sut = new PlotDoesNotExistExceptionMapper();

    @Test
    void PlotIsOccupiedMapperReturnsBadRequestCode() {
        Response response = sut.toResponse(new PlotDoesNotExistException());
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }
}

