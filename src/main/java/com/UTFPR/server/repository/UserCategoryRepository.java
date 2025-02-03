package com.UTFPR.server.repository;

import com.UTFPR.domain.entities.Category;
import com.UTFPR.domain.entities.UserCategory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class UserCategoryRepository {
    private EntityManager em;

    public UserCategoryRepository(EntityManager em) {
        this.em = em;
    }

//    public List<UserCategory> listAllUserCategories() {
//        try {
//            TypedQuery<Category> query = em.createQuery("SELECT c FROM categorias c", Category.class);
//            return query.getResultList();
//        } catch (Exception e) {
//            throw e;
//        }
//    }

    public List<Category> findCategoryById(Long id) {
        try {
            TypedQuery<Category> query = em.createQuery("SELECT c FROM categorias c WHERE c.id = :id", Category.class);
            query.setParameter("id", id);
            return query.getResultList();
        } catch (Exception e) {
            throw e;
        }
    }

    public void save(Category category) {
        em.getTransaction().begin();
        em.persist(category);
        em.getTransaction().commit();
    }

    public void deleteCategory(Category category) {
        em.getTransaction().begin();
        em.remove(category);
        em.flush();
        em.clear();
        em.getTransaction().commit();
    }

    public void editCategoryById(Long id, Category category) {
        em.getTransaction().begin();
        try {
            Query query = em.createQuery(
                    "UPDATE categorias c " +
                            "SET c.nome = :nome " +
                            "WHERE c.id = :id"
            );
            query.setParameter("nome", category.getNome());
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
