package com.UTFPR.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class OperacaoListarAvisosDTO {
    private String operacao;
    private String token;
    private int id;

    public OperacaoListarAvisosDTO(String operacao, String token, int id) {
        this.operacao = operacao;
        this.token = token;
        this.id = id;
    }

    public OperacaoListarAvisosDTO() {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
