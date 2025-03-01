package com.UTFPR.domain.dto;

import com.UTFPR.domain.entities.Category;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class SalvarCategoriaDTO {
    private String operacao;
    private String token;
    private CategoriaDTO categoria;

    public SalvarCategoriaDTO(String operacao, String token, CategoriaDTO categoriaDTO) {
        this.operacao = operacao;
        this.token = token;
        this.categoria = categoriaDTO;
    }

    public SalvarCategoriaDTO() {
    }

    public String getOperacao() {
        return operacao;
    }

    public void setOperacao(String operacao) {
        this.operacao = operacao;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public CategoriaDTO getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaDTO categoriaDTO) {
        this.categoria = categoriaDTO;
    }

}
