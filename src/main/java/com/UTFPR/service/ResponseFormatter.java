package com.UTFPR.service;

import com.UTFPR.domain.dto.ResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class ResponseFormatter {
    private ObjectMapper objectMapper;

    public ResponseFormatter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String formatResponse(ResponseDTO responseDTO) throws IOException {
        return objectMapper.writeValueAsString(responseDTO);
    }
}
