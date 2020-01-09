package nl.han.oose.sapporo.resource.exceptionmapper;

import nl.han.oose.sapporo.persistence.exception.PlotHasNoWaterSourceException;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

class PlotHasNoWaterSourceExceptionMapperTest {
    private PlotHasNoWaterSourceExceptionMapper sut = new PlotHasNoWaterSourceExceptionMapper();

    @Test
    void PlotIsOccupiedMapperReturnsBadRequestCode() {
        Response response = sut.toResponse(new PlotHasNoWaterSourceException());
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }
}
