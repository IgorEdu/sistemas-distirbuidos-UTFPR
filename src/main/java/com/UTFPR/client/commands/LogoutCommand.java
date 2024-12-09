package com.UTFPR.client.commands;

import com.UTFPR.domain.dto.LogoutDTO;
import com.UTFPR.shared.commands.Command;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.PrintWriter;

public class LogoutCommand implements Command {
    private PrintWriter out;
    private ObjectMapper objectMapper;
    private String token;

    public LogoutCommand(PrintWriter out, ObjectMapper objectMapper, String token) {
        this.out = out;
        this.objectMapper = objectMapper;
        this.token = token;
    }

    @Override
    public void execute() throws IOException {
        System.out.println("Realizando logout...");

//        LogoutDTO logoutDTO = new LogoutDTO("logout", null);
        LogoutDTO logoutDTO = new LogoutDTO("logout", token);
        String json = objectMapper.writeValueAsString(logoutDTO);
        System.out.println("Client: " + json);
        out.println(json);
    }
}
