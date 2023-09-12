package com.example.book.configuration;


import com.example.book.constant.Constant;
import com.example.book.model.Role;
import com.example.book.model.User;
import com.example.book.repository.Repository;
import com.example.book.utils.UserQueryUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.annotation.PostConstruct;
import java.util.Objects;
import java.util.Optional;

@Configuration
@ComponentScan
@CrossOrigin("http://localhost:5173")
public class AppConfig {


    Repository<Role, Long> roleRepository = new Repository<>(Role.class);
    Repository<User, Long> userRepository = new Repository<>(User.class);
    private final ApplicationConfig applicationConfig = new ApplicationConfig();

    final UserQueryUtil userQueryUtil = new UserQueryUtil();

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("book-management");
    EntityManager em = emf.createEntityManager();



    @PostConstruct
    public void seedData() {
        if (roleRepository.findAll().isEmpty()) {
            em.getTransaction().begin();
            em.persist(new Role(Constant.ROLE_ADMIN));
            em.persist(new Role(Constant.ROLE_USER));
            em.getTransaction().commit();
            em.close();
            emf.close();
            }
        String password = applicationConfig.passwordEncoder().encode(Constant.ADMIN_PASSWORD);
        if (userQueryUtil.findUserByEmailIgnoreCase("admin@gmail.com").isEmpty()) {
          Optional<Role>  role =  roleRepository.findAll().stream().filter(role1 -> Objects.equals(role1.getName(),Constant.ROLE_ADMIN)).findFirst();
           if(role.isPresent()){
               User user = User.builder()
                       .role(role.get())
                       .dob("2023-08-27")
                       .phone("024000000000")
                       .email("admin@gmail.com")
                       .firstName("Admin")
                       .lastName("Admin")
                       .password(password)
                       .build();
               userRepository.save(user);
           }
        }
        }

    }













