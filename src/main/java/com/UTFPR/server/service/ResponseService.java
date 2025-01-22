package com.UTFPR.server.service;

import com.UTFPR.domain.dto.CategoriaDTO;
import com.UTFPR.domain.dto.ResponseDTO;
import com.UTFPR.domain.dto.UsuarioDTO;
import com.UTFPR.domain.entities.Category;
import com.UTFPR.domain.entities.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.stream.Collectors;

public class ResponseService {


    public ResponseDTO createSuccessResponse(String operacao) {
        ResponseDTO response = new ResponseDTO();
        response.setStatus(200);
//        response.setOperacao(operacao);
        return response;
    }

    public ResponseDTO createSuccessResponseWithToken(String operacao, String token) {
        ResponseDTO response = new ResponseDTO();
        response.setStatus(200);
        response.setToken(token);
//        response.setOperacao(operacao);
        return response;
    }

    public ResponseDTO createSuccessResponseWithMessage(String operacao, String message) {
        ResponseDTO response = new ResponseDTO();
        response.setStatus(201);
        response.setMensagem(message);
        response.setOperacao(operacao);
        return response;
    }


    public ResponseDTO createErrorResponse(String operacao, String message) {
        ResponseDTO response = new ResponseDTO();
        response.setStatus(401);
        response.setOperacao(operacao);
        response.setMensagem(message);
        return response;
    }

    public ResponseDTO returnSuccessResponseUserInformations(String operacao, User user) {
        String usuario   = String.format("""
                "usuario": {
                    "ra": "%s",
                    "senha": "%s",
                    "nome": "%s"
                }""", user.getRa(), user.getSenha() ,user.getNome());

        ResponseDTO response = new ResponseDTO();
        response.setStatus(201);
        response.setOperacao(operacao);
        response.setUsuario(usuario);
        return response;
    }

    public ResponseDTO returnSuccessResponseListUsers(String operacao, List<User> users) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            List<UsuarioDTO> userDTOs = users.stream()
                    .map(user -> new UsuarioDTO(user.getRa(), user.getSenha(), user.getNome()))
                    .collect(Collectors.toList());

            String usuarios = objectMapper.writeValueAsString(userDTOs);

            ResponseDTO response = new ResponseDTO();
            response.setStatus(201);
            response.setOperacao(operacao);
            response.setUsuarios(usuarios);
            return response;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao serializar usuários", e);
        }
    }

    public ResponseDTO returnSuccessResponseListCategories(String operacao, List<Category> categories) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            List<CategoriaDTO> categoryDTOs = (categories != null)
                    ? categories.stream()
                    .map(category -> new CategoriaDTO((int) category.getId(), category.getNome()))
                    .collect(Collectors.toList())
                    : List.of();

            String categorias = objectMapper.writeValueAsString(categoryDTOs);

            ResponseDTO response = new ResponseDTO();
            response.setStatus(201);
            response.setOperacao(operacao);
            response.setCategorias(categorias);
            return response;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao serializar categorias", e);
        }
    }

    public ResponseDTO returnSuccessResponseCategoryInformations(String operacao, Category category) {
        String categoria   = String.format("""
                "categoria": {
                    "id": "%s",
                    "nome": "%s"
                }""", category.getId(), category.getNome());

        ResponseDTO response = new ResponseDTO();
        response.setStatus(201);
        response.setOperacao(operacao);
        response.setCategoria(categoria);
        return response;
    }
}
