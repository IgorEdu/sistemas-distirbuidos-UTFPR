package com.UTFPR.server.infra;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import jakarta.persistence.EntityManager;

public class DatabaseConnection {
    private static EntityManagerFactory emf;

    public static void init() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory("mysql-sistemas-distribuidos");
        }
    }

    public static EntityManager getEntityManager() {
        if (emf == null) {
            throw new IllegalStateException("EntityManagerFactory não inicializado.");
        }
        return emf.createEntityManager();
    }

    public static void close() {
        if (emf != null) {
            emf.close();
        }
    }
}


