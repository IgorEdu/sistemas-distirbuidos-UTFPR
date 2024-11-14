package com.UTFPR.service;

import com.UTFPR.domain.dto.ResponseDTO;

public class ResponseService {
    public ResponseDTO createSuccessResponse(String message) {
        ResponseDTO response = new ResponseDTO();
        response.setStatus(200);
        response.setMensagem(message);
        return response;
    }

    public ResponseDTO createSuccessResponseWithToken(String message, String token) {
        ResponseDTO response = new ResponseDTO();
        response.setStatus(200);
        response.setMensagem(message);
        response.setToken(token);
        return response;
    }

    public ResponseDTO createErrorResponse(String message) {
        ResponseDTO response = new ResponseDTO();
        response.setStatus(401);
        response.setMensagem(message);
        return response;
    }
}
