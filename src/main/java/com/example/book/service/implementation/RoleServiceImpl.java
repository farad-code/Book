package com.example.book.service.implementation;

import com.example.book.dto.role.RoleResponse;
import com.example.book.model.Book;
import com.example.book.model.Role;
import com.example.book.repository.Repository;
import com.example.book.service.RoleService;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;


@ApplicationScoped
public class RoleServiceImpl implements RoleService {

    Repository<Role, Long> roleRepository = new Repository<>(Role.class);

    @Override
    public List<RoleResponse> roles() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream().map(role -> RoleResponse.builder()
                .id(role.getId())
                .name(role.getName())
                .build()).toList();
    }
}
