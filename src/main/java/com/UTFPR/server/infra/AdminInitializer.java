package com.UTFPR.server.infra;

import com.UTFPR.domain.entities.User;
import jakarta.persistence.EntityManager;

public class AdminInitializer {
    public static void initializeAdmin(EntityManager em) {
        String jpql = "SELECT u FROM usuarios u WHERE u.ra = :ra AND u.isAdmin = :is_admin";
        var query = em.createQuery(jpql, User.class);
        query.setParameter("ra", 9999999);
        query.setParameter("is_admin", true);

        if (query.getResultList().isEmpty()) {
            User user = new User("9999999", "admin", "admin");
            user.setAdmin(true);

            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();

            System.out.println("Admin cadastrado");
        }
    }
}
