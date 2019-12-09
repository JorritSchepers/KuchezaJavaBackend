package nl.han.oose.sapporo.resource.exceptionmapper;

import nl.han.oose.sapporo.service.exception.PlotIsAlreadyPurchasedException;
import org.junit.jupiter.api.Test;
import javax.ws.rs.core.Response;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlotIsAlreadyPurchasedExceptionMapperTest {
    private PlotIsAlreadyPurchasedExceptionMapper sut = new PlotIsAlreadyPurchasedExceptionMapper();

    @Test
    public void PlotIsAlreadyPurchasedExceptionMapperReturnsBadRequestCode() {
        Response response = sut.toResponse(new PlotIsAlreadyPurchasedException());
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

}
