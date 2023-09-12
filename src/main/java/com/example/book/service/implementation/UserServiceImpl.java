package com.example.book.service.implementation;

import com.example.book.configuration.ApplicationConfig;
import com.example.book.dto.authentication.RegisterRequest;
import com.example.book.dto.authentication.RegisterResponse;
import com.example.book.dto.user.UserRequest;
import com.example.book.dto.user.UserResponse;
import com.example.book.exception.BadRequest;
import com.example.book.exception.NotFoundException;
import com.example.book.model.Role;
import com.example.book.model.User;
import com.example.book.repository.Repository;
import com.example.book.service.UserService;
import com.example.book.utils.EntityManagerUtil;
import com.example.book.utils.UserQueryUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;


@ApplicationScoped
public class UserServiceImpl implements UserService {

    Repository<User, Long> userRepository = new Repository<>(User.class);
    EntityManager entityManager = EntityManagerUtil.getEntityManager();
    Repository<Role, Long> roleRepository = new Repository<>(Role.class);
    private final ApplicationConfig applicationConfig = new ApplicationConfig();


    @Inject
    UserQueryUtil userQueryUtil;
    @Override
    public UserResponse viewUserDetails(Long id) {
        User user = userRepository.findById(id).orElseThrow();

        return UserResponse.builder()
                .dob(user.getDob())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .phone(user.getPhone())
                .lastName(user.getLastName())
                .id(user.getId())
                .build();
    }

    @Override
    public List<UserResponse> fetchAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(user -> UserResponse.builder()
                .dob(user.getDob())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .phone(user.getPhone())
                .lastName(user.getLastName())
                .id(user.getId())
                .role(user.getRole().getName())
                .build()).toList();
    }

    @Override
    public boolean deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow();
        userRepository.delete(user);
        return true;
    }
    @Override
    public UserResponse updateUserRecord(Long id, UserRequest request) throws NotFoundException {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) throw new NotFoundException("User not found");
        user.get().setDob(request.getDob());
        user.get().setFirstName(request.getFirstName());
        user.get().setPhone(request.getPhone());
        user.get().setLastName(request.getLastName());
        User updatedUser = userRepository.save(user.get());
        return   UserResponse.builder()
                .dob(updatedUser.getDob())
                .email(updatedUser.getEmail())
                .firstName(updatedUser.getFirstName())
                .phone(updatedUser.getPhone())
                .lastName(updatedUser.getLastName())
                .id(updatedUser.getId())
                .build();
    }

    @Override
    public UserResponse loggedInUserInfo() throws NotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> loggedInuser = userQueryUtil.findUserByEmailIgnoreCase(authentication.getName());
        if(loggedInuser.isEmpty()) throw new NotFoundException("User does not exist");
        return   UserResponse.builder()
                .dob(loggedInuser.get().getDob())
                .email(loggedInuser.get().getEmail())
                .firstName(loggedInuser.get().getFirstName())
                .phone(loggedInuser.get().getPhone())
                .lastName(loggedInuser.get().getLastName())
                .id(loggedInuser.get().getId())
                .build();

    }


    @Override
    public RegisterResponse addUser(RegisterRequest request) throws BadRequest {
//        Long roleId = Long.parseLong(String.valueOf(request.getRoleId()));
        Long roleId = request.getRoleId();
        if(userQueryUtil.findUserByEmailIgnoreCase(request.getEmail()).isPresent()){
            throw new BadRequest("User already exist");
        }
        Role role = roleRepository.findById(roleId).orElseThrow();
        PasswordEncoder passwordEncoder = applicationConfig.passwordEncoder();
        User user = User
                .builder()
                .phone(request.getPhone())
                .dob(request.getDob())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .role(role)
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        User savedUser = userRepository.save(user);

        return  RegisterResponse
                .builder()
                .id(savedUser.getId())
                .email(savedUser.getEmail())
                .role(savedUser.getRole().getAuthority())
                .dob(request.getDob())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phone(request.getPhone())
                .build();
    }

}
