package nl.han.oose.sapporo.resource.exceptionmapper;

import nl.han.oose.sapporo.persistence.exception.InvalidLoginInformationException;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

public class InvalidLoginInformationExceptionMapperTest {
    private InvalidLoginInformationExceptionMapper sut = new InvalidLoginInformationExceptionMapper();

    @Test
    void PlotIsOccupiedMapperReturnsBadRequestCode() {
        Response response = sut.toResponse(new InvalidLoginInformationException());
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }
}

