package com.UTFPR.domain.dto;

import com.UTFPR.domain.entities.User;

public class UsuarioDTO {
    private String ra;
    private String senha;
    private String nome;

    public UsuarioDTO(String ra, String senha, String nome) {
        this.ra = ra;
        this.senha = senha;
        this.nome = nome;
    }

    public UsuarioDTO(){

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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public User toUser(){
        return new User(ra, senha, nome);
    }
}
