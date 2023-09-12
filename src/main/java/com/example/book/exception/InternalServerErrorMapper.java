package com.example.book.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public class InternalServerErrorMapper  implements ExceptionMapper<InternalServerError> {


    @Override
    public Response toResponse(InternalServerError exception) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Internal Server Error: " + exception.getMessage())
                .build();
    }
}
