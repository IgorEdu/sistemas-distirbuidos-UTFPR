package com.UTFPR.domain.dto;

import com.UTFPR.domain.contracts.CredentialProvider;
import com.UTFPR.domain.entities.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class EditaUsuarioDTO implements CredentialProvider {
    private String operacao;
    private String token;
    private UsuarioDTO usuarioDTO;

    public EditaUsuarioDTO(String operacao, String token, User usuario) {
        this.operacao = operacao;
        this.token = token;
        this.usuarioDTO = new UsuarioDTO(usuario.getRa(), usuario.getSenha(), usuario.getNome());
    }

    public EditaUsuarioDTO(String operacao, String token, UsuarioDTO usuarioDTO) {
        this.operacao = operacao;
        this.token = token;
        this.usuarioDTO = usuarioDTO;
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

    public UsuarioDTO getUsuarioDTO() {
        return usuarioDTO;
    }

    public void setUsuario(User usuario) {
        this.usuarioDTO = new UsuarioDTO(usuario.getRa(), usuario.getSenha(), usuario.getNome());
    }

    @Override
    @JsonIgnore
    public String getRa() {
        return usuarioDTO.getRa();
    }

    @Override
    @JsonIgnore
    public String getSenha() {
        return usuarioDTO.getSenha();
    }
}
