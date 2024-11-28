package com.UTFPR.server.commands;

import com.UTFPR.domain.dto.ResponseDTO;
import com.UTFPR.server.service.ResponseFormatter;
import com.UTFPR.server.service.ResponseService;
import com.UTFPR.shared.commands.Command;

import java.io.PrintWriter;

public class FallbackCommand implements Command {
    private final String errorMessage;
    private final PrintWriter out;
    private ResponseService responseService;
    private final ResponseFormatter responseFormatter;
    private String operacao;

    public FallbackCommand(String operacao, String errorMessage, PrintWriter out, ResponseService responseService, ResponseFormatter responseFormatter) {
        this.operacao = operacao;
        this.errorMessage = errorMessage;
        this.out = out;
        this.responseService = responseService;
        this.responseFormatter = responseFormatter;
    }

    @Override
    public void execute() {
        try {
            ResponseDTO responseDTO = responseService.createErrorResponse(operacao, errorMessage);
            String response = responseFormatter.formatResponse(responseDTO);
            out.println(response);
            System.out.println("Server: " + response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
