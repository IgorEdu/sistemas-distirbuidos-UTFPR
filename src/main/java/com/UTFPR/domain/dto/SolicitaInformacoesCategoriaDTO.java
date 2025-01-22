package com.UTFPR.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class SolicitaInformacoesCategoriaDTO {
    private String operacao;
    private String token;
    private String id;

    public SolicitaInformacoesCategoriaDTO(String operacao, String token, String id) {
        this.operacao = operacao;
        this.token = token;
        this.id = id;
    }

    public SolicitaInformacoesCategoriaDTO() {
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
