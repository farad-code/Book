package com.example.book.service;

import com.example.book.dto.authentication.LoginRequest;
import com.example.book.dto.authentication.LoginResponse;
import com.example.book.dto.authentication.RegisterRequest;
import com.example.book.dto.authentication.RegisterResponse;

public interface AuthenticationService {

    LoginResponse login(LoginRequest request) throws Exception;

}
