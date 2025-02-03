package com.UTFPR.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class SolicitaInformacoesAvisoDTO {
    private String operacao;
    private String token;
    private String id;

    public SolicitaInformacoesAvisoDTO(String operacao, String token, String id) {
        this.operacao = operacao;
        this.token = token;
        this.id = id;
    }

    public SolicitaInformacoesAvisoDTO() {
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
