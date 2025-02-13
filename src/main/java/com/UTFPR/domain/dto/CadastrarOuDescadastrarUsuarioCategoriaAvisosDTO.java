package com.UTFPR.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class CadastrarOuDescadastrarUsuarioCategoriaAvisosDTO {
    private String operacao;
    private String token;
    private String ra;
    private int categoria;

    public CadastrarOuDescadastrarUsuarioCategoriaAvisosDTO(String operacao, String token, String ra, int categoria) {
        this.operacao = operacao;
        this.token = token;
        this.ra = ra;
        this.categoria = categoria;
    }

    public CadastrarOuDescadastrarUsuarioCategoriaAvisosDTO() {
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

    public String getRa() {
        return ra;
    }

    public void setRa(String ra) {
        this.ra = ra;
    }

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }
}
