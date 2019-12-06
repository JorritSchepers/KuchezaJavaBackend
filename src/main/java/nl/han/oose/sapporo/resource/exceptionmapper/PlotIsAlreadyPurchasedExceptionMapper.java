package nl.han.oose.sapporo.resource.exceptionmapper;

import nl.han.oose.sapporo.service.exception.PlotIsAlreadyPurchasedException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class PlotIsAlreadyPurchasedExceptionMapper implements ExceptionMapper<PlotIsAlreadyPurchasedException> {

    @Override
    public Response toResponse(PlotIsAlreadyPurchasedException e) {
        return Response.status(400)
                .entity("You already own this plot.")
                .build();
    }

}
