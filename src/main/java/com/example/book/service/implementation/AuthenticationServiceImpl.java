package com.example.book.service.implementation;

import com.example.book.configuration.ApplicationConfig;
import com.example.book.configuration.TokenService;
import com.example.book.dto.authentication.LoginRequest;
import com.example.book.dto.authentication.LoginResponse;
import com.example.book.dto.authentication.RegisterRequest;
import com.example.book.dto.authentication.RegisterResponse;
import com.example.book.exception.NotFoundException;
import com.example.book.exception.UnAuthorizedMapper;
import com.example.book.exception.Unauthorized;
import com.example.book.model.Role;
import com.example.book.model.User;
import com.example.book.repository.Repository;
import com.example.book.service.AuthenticationService;
import com.example.book.utils.UserQueryUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@ApplicationScoped
public class AuthenticationServiceImpl implements AuthenticationService {

    Repository<User, Long> userRepository = new Repository<>(User.class);
    Repository<Role, Long> roleRepository = new Repository<>(Role.class);
    private final ApplicationConfig applicationConfig = new ApplicationConfig();
    @Inject
    UserQueryUtil userQueryUtil;

    private final TokenService tokenService = new TokenService();

    @Override
    public LoginResponse login(LoginRequest request) throws Exception {
        Optional<User> user = userQueryUtil.findUserByEmailIgnoreCase(request.getEmail());
        if(user.isEmpty()) throw new Unauthorized("Invalid Credentials");
        AuthenticationManager authenticationManager = applicationConfig.authenticationManager(applicationConfig.userDetailsService());
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),request.getPassword()
                    )
            );

            Map<String, Object> extraClaims = new HashMap<>();
            extraClaims.put("role", user.get().getRole());
            String accessToken = tokenService.generateAccessToken(extraClaims, user.get());
            return new LoginResponse(accessToken);
        }catch (AuthenticationException e){
            throw new Unauthorized(e.getMessage());
        }
    }

}
