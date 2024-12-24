package com.UTFPR.domain.dto;

import com.UTFPR.domain.contracts.CredentialProvider;
import com.UTFPR.domain.entities.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class SolicitaInformacoesUsuarioDTO {
    private String operacao;
    private String token;
    private String ra;

    public SolicitaInformacoesUsuarioDTO(String operacao, String token, String ra) {
        this.operacao = operacao;
        this.token = token;
        this.ra = ra;
    }

    public SolicitaInformacoesUsuarioDTO() {
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
