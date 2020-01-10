package nl.han.oose.sapporo.resource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.core.MultivaluedHashMap;
import java.io.IOException;

import static org.mockito.Mockito.*;

class CORSFilterTest {
    private CORSFilter sut;

    //Mocked objects
    private ContainerRequestContext containerRequestContext;
    private ContainerResponseContext containerResponseContext;

    @BeforeEach
    void setUp() {
        sut = new CORSFilter();

        containerRequestContext = mock(ContainerRequestContext.class);
        containerResponseContext = mock(ContainerResponseContext.class);

        when(containerResponseContext.getHeaders()).thenReturn(new MultivaluedHashMap<String, Object>());
    }

    @Test
    void filter() {
        try {
            sut.filter(containerRequestContext, containerResponseContext);
        } catch (IOException e) {
            e.printStackTrace();
        }

        verify(containerResponseContext, times(5)).getHeaders();
    }
}