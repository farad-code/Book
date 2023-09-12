package com.example.book.utils;


import com.example.book.model.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;

import java.util.Optional;

@ApplicationScoped
public class UserQueryUtil {

    EntityManager entityManager = EntityManagerUtil.getEntityManager();

    public Optional<User> findUserByEmailIgnoreCase(String email) {
        String jpql = "SELECT u FROM User u WHERE lower(u.email) = lower(:email)";
        return entityManager
                .createQuery(jpql, User.class)
                .setParameter("email", email)
                .getResultList()
                .stream()
                .findFirst();
    }
}
