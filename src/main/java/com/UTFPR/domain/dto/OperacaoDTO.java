package com.UTFPR.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class OperacaoDTO {
    private String operacao;

    public OperacaoDTO(String operacao) {
        this.operacao = operacao;
    }

    public OperacaoDTO() {
    }

    public String getOperacao() {
        return operacao;
    }

    public void setOperacao(String operacao) {
        this.operacao = operacao;
    }
}
