package com.UTFPR.commands;

import com.UTFPR.domain.dto.CadastroDTO;
import com.UTFPR.domain.dto.ResponseDTO;
import com.UTFPR.service.ResponseFormatter;
import com.UTFPR.service.ResponseService;
import com.UTFPR.service.UserService;

import java.io.IOException;
import java.io.PrintWriter;

public class CadastroUsuarioCommand implements Command {
    private CadastroDTO cadastroDTO;
    private UserService userService;
    private ResponseService responseService;
    private ResponseFormatter responseFormatter;
    private PrintWriter out;

    public CadastroUsuarioCommand(CadastroDTO cadastroDTO, UserService userService, ResponseService responseService, ResponseFormatter responseFormatter, PrintWriter out) {
        this.cadastroDTO = cadastroDTO;
        this.userService = userService;
        this.responseService = responseService;
        this.responseFormatter = responseFormatter;
        this.out = out;
    }

    @Override
    public void execute() throws IOException {
        ResponseDTO responseDTO;
        if(userService.canRegisterUser(cadastroDTO)) {
            userService.registerUser(cadastroDTO.toEntity());
            responseDTO = responseService.createSuccessResponse("Cadastro bem-sucedido");
        } else{
            responseDTO = responseService.createErrorResponse("Usuario já cadastrado.");
        }

        String response = responseFormatter.formatResponse(responseDTO);
        out.println(response);
    }
}

