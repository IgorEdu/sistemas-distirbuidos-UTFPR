package com.UTFPR.server.commands;

import com.UTFPR.domain.dto.LogoutDTO;
import com.UTFPR.domain.dto.ResponseDTO;
import com.UTFPR.server.service.ResponseFormatter;
import com.UTFPR.server.service.ResponseService;
import com.UTFPR.server.service.UserService;
import com.UTFPR.shared.commands.Command;

import java.io.IOException;
import java.io.PrintWriter;

public class LogoutCommand implements Command {
    private LogoutDTO logoutDTO;
    private UserService userService;
    private ResponseService responseService;
    private ResponseFormatter responseFormatter;
    private PrintWriter out;
    private String clientAddress;

    public LogoutCommand(LogoutDTO logoutDTO, UserService userService, ResponseService responseService, ResponseFormatter responseFormatter, PrintWriter out, String clientAddress) {
        this.logoutDTO = logoutDTO;
        this.userService = userService;
        this.responseService = responseService;
        this.responseFormatter = responseFormatter;
        this.out = out;
        this.clientAddress = clientAddress;
    }

    @Override
    public void execute() throws IOException {
        ResponseDTO responseDTO = responseService.createSuccessResponse(this.logoutDTO.getOperacao());

        String response = responseFormatter.formatResponse(responseDTO);
        System.out.println("Server (" + clientAddress + "): " + response);

        out.println(response);
    }
}
