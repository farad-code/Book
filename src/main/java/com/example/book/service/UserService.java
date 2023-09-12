package com.example.book.service;

import com.example.book.dto.authentication.RegisterRequest;
import com.example.book.dto.authentication.RegisterResponse;
import com.example.book.dto.user.UserRequest;
import com.example.book.dto.user.UserResponse;
import com.example.book.exception.BadRequest;
import com.example.book.exception.NotFoundException;


import java.util.List;

public interface UserService {

    UserResponse viewUserDetails(Long id);

    List<UserResponse> fetchAllUsers();
    boolean deleteUser(Long id);
    RegisterResponse addUser(RegisterRequest request) throws BadRequest;

    UserResponse updateUserRecord(Long id, UserRequest request) throws NotFoundException;

    UserResponse loggedInUserInfo() throws NotFoundException;
}
