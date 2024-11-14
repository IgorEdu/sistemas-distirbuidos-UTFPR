package com.UTFPR.domain.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class LogoutDTO {
    private String operacao;
    private String token;

    public LogoutDTO(String operacao, String token) {
        this.operacao = operacao;
        this.token = token;
    }

    public LogoutDTO() {
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
