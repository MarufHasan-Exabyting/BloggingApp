package com.example.BloggingApplication.dao;

import com.example.BloggingApplication.exception.UserNotFoundException;
import com.example.BloggingApplication.model.EntityMetadata;
import com.example.BloggingApplication.model.User;
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
        TypedQuery<User> query = entityManager.createQuery("From User where metadata.isDeleted = false",User.class);
        return query.getResultList();
    }

    @Override
    public User getUserById(int id) {
        TypedQuery<User> query = entityManager.createQuery("From User where metadata.isDeleted = false AND userId = :id",User.class);
        query.setParameter("id",id);
        List<User> users = query.getResultList();
        if(users.isEmpty())
        {
            throw new UserNotFoundException(String.format("User with User Id %d not found",id));
        }
        return users.getFirst();
    }

    @Override
    public User updateUser(User user) {
        User user_beforeUpdate = entityManager.find(User.class,user.getUserId());
        EntityMetadata entityMetadata = new EntityMetadata(user_beforeUpdate.getMetadata().getCreatedAt(),new Date(System.currentTimeMillis()),false,null);
        user.setMetadata(entityMetadata);
        entityManager.merge(user);
        return entityManager.find(User.class,user.getUserId());
    }

    @Override
    public int deleteUser(int id) {

        User user = entityManager.find(User.class,id);
        if(user == null)
        {
            throw new UserNotFoundException(String.format("User with User Id : %d already deleted",id));
        }

        //setting the deletedAt and isDeleted
        user.getMetadata().setDeletedAt(new Date(System.currentTimeMillis()));
        user.getMetadata().setDeleted(true);
        return 1;
    }

    @Override
    public User getUserByEmail(String userEmail) {
        TypedQuery<User> typedQuery = entityManager.createQuery("From User where userEmail like : email and metadata.isDeleted = false",User.class);
        typedQuery.setParameter("email",userEmail);
        List<User> userList = typedQuery.getResultList();
        if(!userList.isEmpty())
        {
            return userList.getFirst();
        }
        return null;
    }

    @Override
    public User getUserByUserName(String userName) {
        TypedQuery<User> typedQuery = entityManager.createQuery("From User where userName like : name and metadata.isDeleted = false",User.class);
        typedQuery.setParameter("name", userName);
        List<User> userList = typedQuery.getResultList();
        if(userList.isEmpty())
        {
            return null;
        }
        return userList.getFirst();
    }
}
