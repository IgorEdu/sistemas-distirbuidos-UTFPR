package com.UTFPR.factory;

import com.UTFPR.commands.CadastroUsuarioCommand;
import com.UTFPR.commands.Command;
import com.UTFPR.commands.LoginCommand;
import com.UTFPR.commands.LogoutCommand;
import com.UTFPR.domain.dto.CadastroDTO;
import com.UTFPR.domain.dto.LoginDTO;
import com.UTFPR.domain.dto.LogoutDTO;
import com.UTFPR.domain.dto.OperacaoDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.UTFPR.service.ResponseFormatter;
import com.UTFPR.service.ResponseService;
import com.UTFPR.service.UserService;

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
        switch (operacaoDTO.getOperacao()) {
            case "login":
                LoginDTO loginDTO = new ObjectMapper().readValue(inputLine, LoginDTO.class);
                return new LoginCommand(loginDTO, userService, responseService, responseFormatter, out, clientAddress);
            case "cadastrarUsuario":
                CadastroDTO cadastroDTO = new ObjectMapper().readValue(inputLine, CadastroDTO.class);
                return new CadastroUsuarioCommand(cadastroDTO, userService, responseService, responseFormatter, out, clientAddress);
            case "logout":
                LogoutDTO logoutDTO = new ObjectMapper().readValue(inputLine, LogoutDTO.class);
                return new LogoutCommand(logoutDTO, userService, responseService, responseFormatter, out, clientAddress);
            default:
                throw new IllegalArgumentException("Operação desconhecida: " + operacaoDTO.getOperacao());
        }
    }
}

