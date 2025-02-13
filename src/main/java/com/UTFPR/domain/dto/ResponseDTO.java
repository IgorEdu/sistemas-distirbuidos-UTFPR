package com.UTFPR.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDTO {
    private int status;
    private String operacao;
    private String mensagem;
    private String token;
    private UsuarioDTO usuario;
    private CategoriaDTO categoria;
    private AvisoComInfoCategoriaDTO aviso;

    private List<UsuarioDTO> usuarios;
    private List<CategoriaDTO> categorias;
    private List<AvisoComInfoCategoriaDTO> avisosComInfoCategoria;

//    @JsonProperty("categorias")
//    private List<Integer> idsCategorias;

    public ResponseDTO() {
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getOperacao() {
        return operacao;
    }
    public void setOperacao(String operacao) {
        this.operacao = operacao;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
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

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }

    public CategoriaDTO getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaDTO categoria) {
        this.categoria = categoria;
    }

    public List<UsuarioDTO> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<UsuarioDTO> usuarios) {
        this.usuarios = usuarios;
    }

    public List<CategoriaDTO> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<CategoriaDTO> categorias) {
        this.categorias = categorias;
    }

    public List<AvisoComInfoCategoriaDTO> getAvisosComInfoCategoria() {
        return avisosComInfoCategoria;
    }

    public void setAvisosComInfoCategoria(List<AvisoComInfoCategoriaDTO> avisosComInfoCategoria) {
        this.avisosComInfoCategoria = avisosComInfoCategoria;
    }

    public AvisoComInfoCategoriaDTO getAviso() {
        return aviso;
    }

    public void setAviso(AvisoComInfoCategoriaDTO aviso) {
        this.aviso = aviso;
    }

//    public List<Integer> getIdsCategorias() {
//        return idsCategorias;
//    }
//
//    public void setIdsCategorias(List<Integer> idsCategorias) {
//        this.idsCategorias = idsCategorias;
//    }
}

