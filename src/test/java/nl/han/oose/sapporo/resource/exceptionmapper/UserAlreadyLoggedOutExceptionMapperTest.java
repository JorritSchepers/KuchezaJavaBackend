package nl.han.oose.sapporo.resource.exceptionmapper;

import nl.han.oose.sapporo.service.exception.UserAlreadyLoggedOutException;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

class UserAlreadyLoggedOutExceptionMapperTest {
    UserAlreadyLoggedOutExceptionMapper sut = new UserAlreadyLoggedOutExceptionMapper();

    @Test
    void PlotIsOccupiedMapperReturnsBadRequestCode() {
        Response response = sut.toResponse(new UserAlreadyLoggedOutException());
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }
}