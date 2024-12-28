package com.UTFPR.server.repository;

import com.UTFPR.domain.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
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

    public void deleteUser(User user) {
        em.getTransaction().begin();
        em.remove(user);
        em.flush();
        em.clear();
        em.getTransaction().commit();
    }

    public void editUserById(Long id, User user) {
        em.getTransaction().begin();
        try {
            Query query = em.createQuery(
                    "UPDATE usuarios u " +
                            "SET u.senha = :senha, " +
                            "u.nome = :nome, " +
                            "u.ra = :ra " +
                            "WHERE u.id = :id"
            );
            query.setParameter("senha", user.getSenha());
            query.setParameter("nome", user.getNome());
            query.setParameter("ra", user.getRa());
            query.setParameter("id", id);

            query.executeUpdate();
            em.flush();
            em.clear();
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        }
    }

}
