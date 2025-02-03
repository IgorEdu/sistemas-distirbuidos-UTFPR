package com.UTFPR.domain.entities;

import jakarta.persistence.*;

@Entity(name = "avisos")
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @ManyToOne
    @JoinColumn(name = "id_categoria", nullable = false)
    private Category categoria;
    private String titulo;
    private String descricao;

    public Notice(Category categoria, String titulo, String descricao) {
        this.categoria = categoria;
        this.titulo = titulo;
        this.descricao = descricao;
    }

    public Notice() {
    }

    public long getId() {
        return id;
    }

    public Category getCategoria() {
        return categoria;
    }

    public void setIdCategoria(Category categoria) {
        this.categoria = categoria;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
