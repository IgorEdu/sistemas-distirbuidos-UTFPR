package com.UTFPR.repository;

import com.UTFPR.domain.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class UserRepository {
    private EntityManagerFactory emf;

    public UserRepository(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public List<User> findUserByRa(Integer ra) {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<User> query = em.createQuery("SELECT u FROM usuarios u WHERE u.ra = :ra", User.class);
            query.setParameter("ra", ra);
            return query.getResultList();
        }
    }

    public void save(User user) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(user);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}
