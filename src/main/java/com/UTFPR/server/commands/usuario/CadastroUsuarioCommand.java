package com.UTFPR.server.commands.usuario;

import com.UTFPR.domain.dto.CadastroDTO;
import com.UTFPR.server.service.*;
import com.UTFPR.shared.commands.Command;

import java.io.IOException;
import java.io.PrintWriter;

public class CadastroUsuarioCommand implements Command {
    private CadastroDTO cadastroDTO;
    private UserService userService;
    private ResponseService responseService;
    private ResponseFormatter responseFormatter;
    private PrintWriter out;
    private String clientAddress;

    public CadastroUsuarioCommand(CadastroDTO cadastroDTO, UserService userService, ResponseService responseService, ResponseFormatter responseFormatter, PrintWriter out, String clientAddress) {
        this.cadastroDTO = cadastroDTO;
        this.userService = userService;
        this.responseService = responseService;
        this.responseFormatter = responseFormatter;
        this.out = out;
        this.clientAddress = clientAddress;
    }

    @Override
    public void execute() throws IOException {
//        ResponseDTO responseDTO;
//        if(userService.canRegisterUser(cadastroDTO)) {
//            userService.registerUser(cadastroDTO.toEntity());
//            responseDTO = responseService.createSuccessResponseWithMessage("Cadastro bem-sucedido");
//        } else{
//            responseDTO = responseService.createErrorResponse(cadastroDTO.getOperacao(),"Usuario já cadastrado.");
//        }

        CadastroUsuarioFacade cadastroUsuarioFacade = new CadastroUsuarioFacade(userService, responseService, responseFormatter);

        String response = cadastroUsuarioFacade.handleCadastroUsuario(cadastroDTO, clientAddress);
        out.println(response);

//        String response = responseFormatter.formatResponse(responseDTO);
//        System.out.println("Server (" + clientAddress + "): " + response);
//        out.println(response);
    }
}

