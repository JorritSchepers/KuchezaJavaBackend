package nl.han.oose.sapporo.resource.exceptionmapper;

import nl.han.oose.sapporo.persistence.exception.InsufficientFundsException;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InsufficientFundsMapperTest {
    private InsufficientFundsMapper sut = new InsufficientFundsMapper();

    @Test
    void PersistenceMapperReturnsBadRequestCode() {
        Response response = sut.toResponse(new InsufficientFundsException());
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }
}
