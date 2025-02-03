package com.UTFPR.domain.dto;

import com.UTFPR.domain.contracts.CredentialProvider;
import com.UTFPR.domain.entities.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class EditaUsuarioDTO implements CredentialProvider {
    private String operacao;
    private String token;
    private UsuarioDTO usuario;

    public EditaUsuarioDTO(String operacao, String token, User usuario) {
        this.operacao = operacao;
        this.token = token;
        this.usuario = new UsuarioDTO(usuario.getRa(), usuario.getSenha(), usuario.getNome());
    }

    public EditaUsuarioDTO(String operacao, String token, UsuarioDTO usuarioDTO) {
        this.operacao = operacao;
        this.token = token;
        this.usuario = usuarioDTO;
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

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = new UsuarioDTO(usuario.getRa(), usuario.getSenha(), usuario.getNome());
    }

    @Override
    @JsonIgnore
    public String getRa() {
        return usuario.getRa();
    }

    @Override
    @JsonIgnore
    public String getSenha() {
        return usuario.getSenha();
    }
}
