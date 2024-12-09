package com.UTFPR.shared.commands;

import com.UTFPR.domain.dto.*;
import com.UTFPR.server.commands.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.UTFPR.server.service.ResponseFormatter;
import com.UTFPR.server.service.ResponseService;
import com.UTFPR.server.service.UserService;

import java.io.IOException;
import java.io.PrintWriter;

public class CommandFactory {
    private UserService userService;
    private ResponseService responseService;
    private ResponseFormatter responseFormatter;
    private PrintWriter out;

    public CommandFactory(UserService userService, ResponseService responseService, ResponseFormatter responseFormatter, PrintWriter out) {
        this.userService = userService;
        this.responseService = responseService;
        this.responseFormatter = responseFormatter;
        this.out = out;
    }

    public Command createCommand(OperacaoDTO operacaoDTO, String inputLine, String clientAddress) throws IOException {

        System.out.println("Client (" + clientAddress + "): " + inputLine);
        if (operacaoDTO.getOperacao() == null) {
            return new FallbackCommand(operacaoDTO.getOperacao(),
                    "Operacao nao encontrada",
                    out, responseService, responseFormatter
            );
        }

        switch (operacaoDTO.getOperacao()) {
            case "login":
                LoginDTO loginDTO = null;
                try {
                    loginDTO = new ObjectMapper().readValue(inputLine, LoginDTO.class);
                    return new LoginCommand(loginDTO, userService, responseService, responseFormatter, out, clientAddress);
                } catch (IOException e) {
                    ResponseDTO responseDTO  = responseService.createErrorResponse("login", "Não foi possível ler o json recebido");
                    String response = responseFormatter.formatResponse(responseDTO);
                    System.out.println("Server (" + clientAddress + "): " + response);
                    out.println(response);
                }
            case "cadastrarUsuario":
                try {
                    CadastroDTO cadastroDTO = new ObjectMapper().readValue(inputLine, CadastroDTO.class);
                    return new CadastroUsuarioCommand(cadastroDTO, userService, responseService, responseFormatter, out, clientAddress);
                } catch (IOException e) {
                    ResponseDTO responseDTO  = responseService.createErrorResponse("cadastrarUsuario", "Não foi possível ler o json recebido");
                    String response = responseFormatter.formatResponse(responseDTO);
                    System.out.println("Server (" + clientAddress + "): " + response);
                    out.println(response);
                }
            case "logout":
                LogoutDTO logoutDTO = new ObjectMapper().readValue(inputLine, LogoutDTO.class);
                return new LogoutCommand(logoutDTO, userService, responseService, responseFormatter, out, clientAddress);
            case "informacoesUsuario":
//                System.out.println("testando informações");
                SolicitaInformacoesUsuarioDTO solicitaInformacoesUsuarioDTO = new ObjectMapper().readValue(inputLine, SolicitaInformacoesUsuarioDTO.class);
                return new InformacoesUsuarioCommand(solicitaInformacoesUsuarioDTO, userService, responseService, responseFormatter, out, clientAddress);
            default:
                return new FallbackCommand(operacaoDTO.getOperacao(),
                        "Operacao nao encontrada",
                        out, responseService, responseFormatter
                );
        }
    }
}

