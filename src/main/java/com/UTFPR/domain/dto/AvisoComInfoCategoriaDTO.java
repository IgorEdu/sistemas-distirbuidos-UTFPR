package com.UTFPR.domain.dto;

import com.UTFPR.domain.entities.Category;
import com.UTFPR.domain.entities.Notice;

public class AvisoComInfoCategoriaDTO {
    private int id;
    private String titulo;
    private String descricao;
    private Category categoria;

    public AvisoComInfoCategoriaDTO(int id, String titulo, String descricao, Category categoria) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.categoria = categoria;
    }

    public AvisoComInfoCategoriaDTO(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Category getCategoria() {
        return categoria;
    }

    public void setCategoria(Category categoria) {
        this.categoria = categoria;
    }
}
