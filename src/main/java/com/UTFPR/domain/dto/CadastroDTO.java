package com.UTFPR.domain.dto;

import com.UTFPR.domain.entities.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class CadastroDTO {
    private String operacao;
    private int ra;
    private String senha;
    private String nome;

    public CadastroDTO(String operacao, int ra, String senha, String nome) {
        this.operacao = operacao;
        this.ra = ra;
        this.senha = senha;
        this.nome = nome;
    }

    public CadastroDTO() {
    }

    public User toEntity() {
        return new User(ra, senha, nome);
    }

    public String getOperacao() {
        return operacao;
    }

    public void setOperacao(String operacao) {
        this.operacao = operacao;
    }

    public int getRa() {
        return ra;
    }

    public void setRa(int ra) {
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
}
