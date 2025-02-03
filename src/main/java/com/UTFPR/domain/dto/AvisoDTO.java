package com.UTFPR.domain.dto;

import com.UTFPR.domain.entities.Category;
import com.UTFPR.domain.entities.Notice;
import com.UTFPR.server.service.CategoryService;

public class AvisoDTO {
    private int id;
    private int categoria;
    private String titulo;
    private String descricao;

    public AvisoDTO(int id, int idCategoria, String titulo, String descricao) {
        this.id = id;
        this.categoria = idCategoria;
        this.titulo = titulo;
        this.descricao = descricao;
    }

    public AvisoDTO(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) {
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

    public Notice toNotice(CategoryService categoryService){
        Category category = categoryService.getCategoryById(categoria);
        return new Notice(category, titulo, descricao);
    }
}
