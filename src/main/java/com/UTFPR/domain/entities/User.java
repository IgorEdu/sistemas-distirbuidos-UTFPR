package com.UTFPR.domain.entities;

import java.util.UUID;

import jakarta.persistence.*;

@Entity(name = "usuarios")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String ra;
    private String senha;
    private String nome;
    @Column(name = "is_admin")
    private boolean isAdmin;

    public User(String ra, String senha, String nome) {
        this.ra = ra;
        this.senha = senha;
        this.nome = nome;
        this.isAdmin = false;
    }

    public User() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRa() {
        return ra;
    }

    public void setRa(String ra) {
        this.ra = ra;
    }

    public boolean isSenha(String senha) {
        return this.senha.equals(senha);
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
}
