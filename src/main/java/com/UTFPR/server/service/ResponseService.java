package com.UTFPR.server.service;

import com.UTFPR.domain.dto.ResponseDTO;
import com.UTFPR.domain.entities.User;

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
        response.setStatus(200);
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
        String message = String.format("""
                {
                    "nome": "%s",
                    "ra": "%s"
                }
                """, user.getNome(), user.getRa());

        ResponseDTO response = new ResponseDTO();
        response.setStatus(200);
        response.setMensagem(message);
//        response.setOperacao(operacao);
        return response;
    }
}
