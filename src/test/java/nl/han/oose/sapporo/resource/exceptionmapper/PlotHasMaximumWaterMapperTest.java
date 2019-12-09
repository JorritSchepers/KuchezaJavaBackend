package nl.han.oose.sapporo.resource.exceptionmapper;

import nl.han.oose.sapporo.persistence.exception.PlotHasMaximumWaterException;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlotHasMaximumWaterMapperTest {
    private PlotHasMaximumWaterMapper sut = new PlotHasMaximumWaterMapper();

    @Test
    void PersistenceMapperReturnsBadRequestCode() {
        Response response = sut.toResponse(new PlotHasMaximumWaterException());
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }
}
