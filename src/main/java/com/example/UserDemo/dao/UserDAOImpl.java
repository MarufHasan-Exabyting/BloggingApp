package com.example.UserDemo.dao;

import com.example.UserDemo.dto.CreateUserDTO;
import com.example.UserDemo.dto.ResponseUserDTO;
import com.example.UserDemo.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

    private EntityManager entityManager;

    @Autowired
    public UserDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public User createUser(User user) {
        entityManager.persist(user);
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        TypedQuery<User> query = entityManager.createQuery("From User",User.class);
        return query.getResultList();
    }

    @Override
    public User getUserById(int id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User updateUser(User user) {
        User user_beforeUpdate = entityManager.find(User.class,user.getUserId());
        user.setCreatedAt(user_beforeUpdate.getCreatedAt());
        user.setUpdatedAt(new Date(System.currentTimeMillis()));
        entityManager.merge(user);
        return entityManager.find(User.class,user.getUserId());
    }

    @Override
    public void deleteUser(int id) {
        User user = entityManager.find(User.class,id);
        entityManager.remove(user);
    }
}