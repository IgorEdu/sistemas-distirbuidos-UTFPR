package com.UTFPR.domain.dto;

import com.UTFPR.domain.contracts.CredentialProvider;

public class LoginDTO implements CredentialProvider {
    private String operacao;
    private String ra;
    private String senha;

    public LoginDTO(String operacao, String ra, String senha) {
        this.operacao = operacao;
        this.ra = ra;
        this.senha = senha;
    }

    public LoginDTO() {
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
