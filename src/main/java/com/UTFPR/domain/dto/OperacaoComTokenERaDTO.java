package com.UTFPR.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class OperacaoComTokenERaDTO {
    private String operacao;
    private String token;
    private String ra;

    public OperacaoComTokenERaDTO(String operacao, String token, String ra) {
        this.operacao = operacao;
        this.token = token;
        this.ra = ra;
    }

    public OperacaoComTokenERaDTO() {
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
}
