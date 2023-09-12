package com.example.book.controller;

import com.example.book.dto.authentication.RegisterRequest;
import com.example.book.dto.authentication.RegisterResponse;
import com.example.book.dto.user.UserRequest;
import com.example.book.dto.user.UserResponse;
import com.example.book.exception.BadRequest;
import com.example.book.exception.NotFoundException;
import com.example.book.service.UserService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;


import java.util.List;

@Path("user")
@CrossOrigin("http://localhost:5173/")
public class UserController {


    @Inject
    private UserService userService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserResponse> allUsers(){
        return userService.fetchAllUsers();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public UserResponse viewUserDetails(@PathParam("id") Long id){
        return userService.viewUserDetails(id);
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public UserResponse updateUserRecord(UserRequest request,@PathParam("id") Long id) throws NotFoundException {
        return userService.updateUserRecord(id,request);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public boolean deleteUserRecord(@PathParam("id") Long id){
        return userService.deleteUser(id);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("logged-in-user-info")
    @PreAuthorize("hasAuthority('ADMIN')")
    public UserResponse loggedInUserInfo() throws NotFoundException {
        return userService.loggedInUserInfo();
    }

    @Path("register")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public RegisterResponse addUser(RegisterRequest request) throws BadRequest {
        return userService.addUser(request);
    }

}
