package com.UTFPR.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class SalvarAvisoDTO {
    private String operacao;
    private String token;
    private AvisoDTO aviso;

    public SalvarAvisoDTO(String operacao, String token, AvisoDTO avisoDTO) {
        this.operacao = operacao;
        this.token = token;
        this.aviso = avisoDTO;
    }

    public SalvarAvisoDTO() {
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

    public AvisoDTO getAviso() {
        return aviso;
    }

    public void setAviso(AvisoDTO aviso) {
        this.aviso = aviso;
    }
}
