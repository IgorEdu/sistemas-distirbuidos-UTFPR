package com.UTFPR.commands;

import com.UTFPR.domain.dto.LoginDTO;
import com.UTFPR.domain.dto.ResponseDTO;
import com.UTFPR.repository.UserRepository;
import com.UTFPR.service.ResponseFormatter;
import com.UTFPR.service.ResponseService;
import com.UTFPR.service.UserService;

import java.io.IOException;
import java.io.PrintWriter;

public class LoginCommand implements Command {
    private LoginDTO loginDTO;
    private UserService userService;
    private ResponseService responseService;
    private ResponseFormatter responseFormatter;
    private PrintWriter out;
    private String inputLine;

    public LoginCommand(LoginDTO loginDTO, UserService userService, ResponseService responseService, ResponseFormatter responseFormatter, PrintWriter out, String inputLine) {
        this.loginDTO = loginDTO;
        this.userService = userService;
        this.responseService = responseService;
        this.responseFormatter = responseFormatter;
        this.out = out;
        this.inputLine = inputLine;
    }

    @Override
    public void execute() throws IOException {
        ResponseDTO responseDTO;
        if(userService.isValidUser(loginDTO)){
            String token = userService.generateToken(loginDTO.getRa());
            responseDTO = responseService.createSuccessResponseWithToken("Login bem-sucedido", token);
        } else{
            responseDTO = responseService.createErrorResponse("Erro ao realizar login.");
        }

        String response = responseFormatter.formatResponse(responseDTO);
        out.println(response);
    }
}
