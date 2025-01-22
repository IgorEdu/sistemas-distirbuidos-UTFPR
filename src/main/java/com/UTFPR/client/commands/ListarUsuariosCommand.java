package com.UTFPR.client.commands;

import com.UTFPR.domain.dto.OperacaoComTokenDTO;
import com.UTFPR.domain.dto.SolicitaInformacoesUsuarioDTO;
import com.UTFPR.shared.commands.Command;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class ListarUsuariosCommand implements Command {
    private PrintWriter out;
    private BufferedReader stdIn;
    private ObjectMapper objectMapper;
    private final String token;

    public ListarUsuariosCommand(PrintWriter out, BufferedReader stdIn, ObjectMapper objectMapper, String token) {
        this.out = out;
        this.stdIn = stdIn;
        this.objectMapper = objectMapper;
        this.token = token;
    }

    @Override
    public void execute() throws IOException {

        OperacaoComTokenDTO operacaoComTokenDTO = new OperacaoComTokenDTO("listarUsuarios", token);

        String json = objectMapper.writeValueAsString(operacaoComTokenDTO);
        System.out.println("Client: " + json);
        out.println(json);
    }
}
