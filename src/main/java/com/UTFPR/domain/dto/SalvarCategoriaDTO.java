package com.UTFPR.domain.dto;

import com.UTFPR.domain.entities.Category;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class SalvarCategoriaDTO {
    private String operacao;
    private String token;
    private CategoriaDTO categoriaDTO;

    public SalvarCategoriaDTO(String operacao, String token, CategoriaDTO categoriaDTO) {
        this.operacao = operacao;
        this.token = token;
        this.categoriaDTO = categoriaDTO;
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

    public CategoriaDTO getCategoriaDTO() {
        return categoriaDTO;
    }

    public void setCategoriaDTO(CategoriaDTO categoriaDTO) {
        this.categoriaDTO = categoriaDTO;
    }

}
