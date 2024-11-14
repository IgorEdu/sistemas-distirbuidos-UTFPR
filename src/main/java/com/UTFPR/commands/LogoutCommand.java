package com.UTFPR.commands;

import com.UTFPR.domain.dto.LogoutDTO;
import com.UTFPR.domain.dto.ResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.UTFPR.service.ResponseFormatter;
import com.UTFPR.service.ResponseService;
import com.UTFPR.service.UserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class LogoutCommand implements Command {
    private LogoutDTO logoutDTO;
    private UserService userService;
    private ResponseService responseService;
    private ResponseFormatter responseFormatter;
    private PrintWriter out;
    private String inputLine;

    public LogoutCommand(LogoutDTO logoutDTO, UserService userService, ResponseService responseService, ResponseFormatter responseFormatter, PrintWriter out, String inputLine) {
        this.logoutDTO = logoutDTO;
        this.userService = userService;
        this.responseService = responseService;
        this.responseFormatter = responseFormatter;
        this.out = out;
        this.inputLine = inputLine;
    }

    @Override
    public void execute() throws IOException {
        ResponseDTO responseDTO = responseService.createSuccessResponse("Logout bem-sucedido");

        String response = responseFormatter.formatResponse(responseDTO);

//                    System.out.println("Client (" + clientSocket.getInetAddress() + "): " + inputLine);
//                    System.out.println("Cliente desconectado: " + clientSocket.getInetAddress());
//                    System.out.println("Server (" + clientSocket.getInetAddress() + "): " + response);

        out.println(response);
        out.close();
//        in.close();
//        clientSocket.close();
    }
}
