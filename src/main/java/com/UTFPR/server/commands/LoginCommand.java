package com.UTFPR.server.commands;

import com.UTFPR.domain.dto.LoginDTO;
import com.UTFPR.server.service.LoginFacade;
import com.UTFPR.server.service.ResponseFormatter;
import com.UTFPR.server.service.ResponseService;
import com.UTFPR.server.service.UserService;
import com.UTFPR.shared.commands.Command;

import java.io.IOException;
import java.io.PrintWriter;

public class LoginCommand implements Command {
    private LoginDTO loginDTO;
    private UserService userService;
    private ResponseService responseService;
    private ResponseFormatter responseFormatter;
    private PrintWriter out;
    private String clientAddress;

    public LoginCommand(LoginDTO loginDTO, UserService userService, ResponseService responseService, ResponseFormatter responseFormatter, PrintWriter out, String clientAddress) {
        this.loginDTO = loginDTO;
        this.userService = userService;
        this.responseService = responseService;
        this.responseFormatter = responseFormatter;
        this.out = out;
        this.clientAddress = clientAddress;
    }

    @Override
    public void execute() throws IOException {
        LoginFacade loginFacade = new LoginFacade(userService, responseService, responseFormatter);

        String response = loginFacade.handleLogin(loginDTO, clientAddress);
        out.println(response);
    }
}
