package com.UTFPR.server.repository;

import com.UTFPR.domain.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class UserRepository {
    private EntityManager em;

    public UserRepository(EntityManager em) {
        this.em = em;
    }

    public List<User> findUserByRa(String ra) {
        try {
            TypedQuery<User> query = em.createQuery("SELECT u FROM usuarios u WHERE u.ra = :ra", User.class);
            query.setParameter("ra", ra);
            return query.getResultList();
        } catch (Exception e) {
            throw e;
        }
    }

    public void save(User user) {
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
    }
}
