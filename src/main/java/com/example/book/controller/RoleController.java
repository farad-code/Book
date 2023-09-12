package com.example.book.controller;


import com.example.book.dto.role.RoleResponse;
import com.example.book.service.RoleService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@Path("/role")
@CrossOrigin("*")
public class RoleController {

    @Inject
    RoleService roleService;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<RoleResponse> fetchAllBooks(){
        return roleService.roles();
    }
}
