package com.UTFPR.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class OperacaoComTokenDTO {
    private String operacao;
    private String token;

    public OperacaoComTokenDTO(String operacao, String token) {
        this.operacao = operacao;
        this.token = token;
    }

    public OperacaoComTokenDTO() {
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
}
