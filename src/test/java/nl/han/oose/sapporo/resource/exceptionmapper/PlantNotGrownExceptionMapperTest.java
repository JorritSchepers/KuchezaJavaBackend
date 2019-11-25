package nl.han.oose.sapporo.resource.exceptionmapper;

import nl.han.oose.sapporo.persistence.exception.InsufficientFundsException;
import nl.han.oose.sapporo.persistence.exception.PlantNotGrownException;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlantNotGrownExceptionMapperTest {
    private PlantNotGrownExceptionMapper sut = new PlantNotGrownExceptionMapper();

    @Test
    void PersistenceMapperReturnsBadRequestCode() {
        Response response = sut.toResponse(new PlantNotGrownException());
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }
}

