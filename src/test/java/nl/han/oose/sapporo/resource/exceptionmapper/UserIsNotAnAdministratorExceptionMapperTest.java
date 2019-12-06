package nl.han.oose.sapporo.resource.exceptionmapper;

import nl.han.oose.sapporo.service.exception.UserIsNotAnAdministratorException;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.*;

class UserIsNotAnAdministratorExceptionMapperTest {
    private UserIsNotAnAdministratorExceptionMapper sut = new UserIsNotAnAdministratorExceptionMapper();

    @Test
    void UserIsNotAnAdministratorExceptionMapperReturnsForbiddenHttpCode() {
        Response response = sut.toResponse(new UserIsNotAnAdministratorException());
        assertEquals(Response.Status.FORBIDDEN.getStatusCode(), response.getStatus());
    }
}
