package com.UTFPR.domain.dto;

import com.UTFPR.domain.contracts.CredentialProvider;
import com.UTFPR.domain.entities.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class EditaUsuarioDTO implements CredentialProvider {
    private String operacao;
    private String token;
    private User usuario;

    public EditaUsuarioDTO(String operacao, String token, User usuario) {
        this.operacao = operacao;
        this.token = token;
        this.usuario = usuario;
    }

    public EditaUsuarioDTO() {
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

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    @Override
    public String getRa() {
        return usuario.getRa();
    }

    @Override
    public String getSenha() {
        return usuario.getSenha();
    }
}
