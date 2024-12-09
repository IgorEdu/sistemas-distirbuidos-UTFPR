package com.UTFPR.domain.dto;

import com.UTFPR.domain.contracts.CredentialProvider;
import com.UTFPR.domain.entities.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class SolicitaInformacoesUsuarioDTO implements CredentialProvider {
    private String operacao;
    private String ra;
    private String senha;

    public SolicitaInformacoesUsuarioDTO(String operacao, String ra, String senha) {
        this.operacao = operacao;
        this.ra = ra;
        this.senha = senha;
    }

    public SolicitaInformacoesUsuarioDTO() {
    }

    public String getOperacao() {
        return operacao;
    }

    public void setOperacao(String operacao) {
        this.operacao = operacao;
    }

    public String getRa() {
        return ra;
    }

    public void setRa(String ra) {
        this.ra = ra;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
