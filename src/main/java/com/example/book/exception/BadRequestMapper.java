package com.example.book.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public class BadRequestMapper implements ExceptionMapper<BadRequest> {
    @Override
    public Response toResponse(BadRequest exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity("Bad Request: " + exception.getMessage())
                .build();
    }
}
