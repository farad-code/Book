package com.example.book.repository;


import com.example.book.utils.EntityManagerUtil;
import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import org.springframework.data.repository.CrudRepository;



public class Repository<T, Id> implements CrudRepository<T, Id> {

    private final Class<T> entityClass;
    EntityTransaction transaction = null;
    EntityManager entityManager = EntityManagerUtil.getEntityManager();
    public Repository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }
    @Override
    public Optional<T> findById(Id id) {

        T entity = entityManager.find(entityClass, id);
        return Optional.ofNullable(entity);
    }

    public Optional<T> findByName(String name) {

        T entity = entityManager.find(entityClass, name);
        return Optional.ofNullable(entity);
    }
    @Override
    public boolean existsById(Id id) {
        return false;
    }

    @Override
    public <S extends T> S save(S entity) {

        transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(entity);
        transaction.commit();
        return entity;
    }

    @Override
    public <S extends T> Iterable<S> saveAll(Iterable<S> entities) {
        List<S> savedEntities = new ArrayList<>();

        for (S entity : entities) {
            if (entityManager.contains(entity)) {
                entity = entityManager.merge(entity);
            } else {
                entityManager.persist(entity);
            }
            savedEntities.add(entity);
        }

        return savedEntities;
    }

    @Override
    public List<T> findAll() {
        String jpql = "SELECT t FROM " + entityClass.getSimpleName() + " t";
        TypedQuery<T> query = entityManager.createQuery(jpql, entityClass);
        return query.getResultList();
    }

    @Override
    public Iterable<T> findAllById(Iterable<Id> ids) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Id id) {
        T entity = entityManager.find(entityClass, id);
        if (entity != null) {
            entityManager.remove(entity);
        }
    }



    @Override
    public void delete(T entity) {
        transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.remove(entity);
        transaction.commit();
    }

    @Override
    public void deleteAllById(Iterable<? extends Id> ids) {

    }


    @Override
    public void deleteAll(Iterable<? extends T> entities) {

    }

    @Override
    public void deleteAll() {

    }


}

