package com.UTFPR.client.commands;

import com.UTFPR.domain.dto.OperacaoComTokenERaDTO;
import com.UTFPR.shared.commands.Command;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class ListarAvisosUsuarioInicialCommand implements Command {
    private PrintWriter out;
    private BufferedReader stdIn;
    private ObjectMapper objectMapper;
    private final String token;

    public ListarAvisosUsuarioInicialCommand(PrintWriter out, BufferedReader stdIn, ObjectMapper objectMapper, String token) {
        this.out = out;
        this.stdIn = stdIn;
        this.objectMapper = objectMapper;
        this.token = token;
    }

    @Override
    public void execute() throws IOException {
        OperacaoComTokenERaDTO OperacaoComTokenERaDTO = new OperacaoComTokenERaDTO("listarUsuarioAvisos", token, token);

        String json = objectMapper.writeValueAsString(OperacaoComTokenERaDTO);
        System.out.println("Client: " + json);
        out.println(json);
    }
}
