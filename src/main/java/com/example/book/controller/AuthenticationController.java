package com.example.book.controller;

import com.example.book.dto.authentication.LoginRequest;
import com.example.book.dto.authentication.LoginResponse;
import com.example.book.dto.authentication.RegisterRequest;
import com.example.book.dto.authentication.RegisterResponse;
import com.example.book.service.AuthenticationService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.springframework.web.bind.annotation.CrossOrigin;

@Path("/auth")
@CrossOrigin("*")
public class AuthenticationController {

    @Inject
    private AuthenticationService authenticationService;



@Path("login")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(LoginRequest request) throws Exception {
       LoginResponse response =  authenticationService.login(request);
    return Response.ok(response).header("Access-Control-Allow-Origin", "*").build();
    }
}
