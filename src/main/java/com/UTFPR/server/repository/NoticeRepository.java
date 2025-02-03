package com.UTFPR.server.repository;

import com.UTFPR.domain.entities.Category;
import com.UTFPR.domain.entities.Notice;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class NoticeRepository {
    private EntityManager em;

    public NoticeRepository(EntityManager em) {
        this.em = em;
    }

    public List<Notice> listAllNotices() {
        try {
            TypedQuery<Notice> query = em.createQuery("SELECT a FROM avisos a", Notice.class);
            return query.getResultList();
        } catch (Exception e) {
            throw e;
        }
    }

    public List<Notice> listAllNoticesOfCategory(int categoryId) {
        try {
            TypedQuery<Notice> query = em.createQuery("SELECT a FROM avisos a WHERE a.id_categoria = :categotyId", Notice.class);
            query.setParameter("categoryId", categoryId);
            return query.getResultList();
        } catch (Exception e) {
            throw e;
        }
    }

    public List<Notice> findNoticeById(Long id) {
        try {
            TypedQuery<Notice> query = em.createQuery("SELECT a FROM avisos a WHERE a.id = :id", Notice.class);
            query.setParameter("id", id);
            return query.getResultList();
        } catch (Exception e) {
            throw e;
        }
    }

    public void save(Notice notice) {
        em.getTransaction().begin();
        em.persist(notice);
        em.getTransaction().commit();
    }

    public void deleteNotice(Notice notice) {
        em.getTransaction().begin();
        em.remove(notice);
        em.flush();
        em.clear();
        em.getTransaction().commit();
    }

    public void editNoticeById(Long id, Notice notice) {
        em.getTransaction().begin();
        try {
            Query query = em.createQuery(
                    "UPDATE avisos a " +
                            "SET a.id_categoria = :id_categoria " +
                            "SET a.titulo = :titulo " +
                            "SET a.descricao = :descricao " +
                            "WHERE c.id = :id"
            );
            query.setParameter("id_categoria", notice.getCategoria().getId());
            query.setParameter("titulo", notice.getTitulo());
            query.setParameter("descricao", notice.getDescricao());
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
