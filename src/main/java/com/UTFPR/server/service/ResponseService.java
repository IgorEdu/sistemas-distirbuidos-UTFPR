package com.UTFPR.server.service;

import com.UTFPR.domain.dto.ResponseDTO;

public class ResponseService {


    public ResponseDTO createSuccessResponse() {
        ResponseDTO response = new ResponseDTO();
        response.setStatus(200);
        return response;
    }

    public ResponseDTO createSuccessResponseWithToken(String token) {
        ResponseDTO response = new ResponseDTO();
        response.setStatus(200);
        response.setToken(token);
        return response;
    }

    public ResponseDTO createSuccessResponseWithMessage(String message) {
        ResponseDTO response = new ResponseDTO();
        response.setStatus(200);
        response.setMensagem(message);
        return response;
    }


    public ResponseDTO createErrorResponse(String operacao, String message) {
        ResponseDTO response = new ResponseDTO();
        response.setStatus(401);
        response.setOperacao(operacao);
        response.setMensagem(message);
        return response;
    }
}
