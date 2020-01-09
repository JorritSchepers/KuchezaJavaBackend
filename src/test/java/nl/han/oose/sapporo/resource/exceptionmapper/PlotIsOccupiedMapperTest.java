package nl.han.oose.sapporo.resource.exceptionmapper;

import nl.han.oose.sapporo.persistence.exception.PlotIsOccupiedException;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlotIsOccupiedMapperTest {
    private PlotIsOccupiedMapper sut = new PlotIsOccupiedMapper();

    @Test
    void PlotIsOccupiedMapperReturnsBadRequestCode() {
        Response response = sut.toResponse(new PlotIsOccupiedException());
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }
}
