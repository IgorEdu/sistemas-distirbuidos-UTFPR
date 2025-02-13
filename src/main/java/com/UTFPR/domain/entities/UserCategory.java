package com.UTFPR.domain.entities;

import jakarta.persistence.*;

@Entity(name = "usuarios_categorias")
public class UserCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_categoria", nullable = false)
    private Category category;

    public UserCategory(long id, User user, Category category) {
        this.id = id;
        this.user = user;
        this.category = category;
    }

    public UserCategory(User user, Category category) {
        this.user = user;
        this.category = category;
    }

    public UserCategory() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
