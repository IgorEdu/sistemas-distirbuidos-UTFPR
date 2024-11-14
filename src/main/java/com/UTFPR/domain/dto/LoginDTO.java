package com.UTFPR.domain.dto;

public class LoginDTO {
    private String operacao;
    private int ra;
    private String senha;

    public LoginDTO(String operacao, int ra, String senha) {
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
}
