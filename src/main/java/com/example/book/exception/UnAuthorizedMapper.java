package com.example.book.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public class UnAuthorizedMapper implements ExceptionMapper<Unauthorized> {
    @Override
    public Response toResponse(Unauthorized exception) {
       return Response.status(Response.Status.UNAUTHORIZED)
                .entity("Unauthorized Error: " + exception.getMessage())
                .build();
    }
}
