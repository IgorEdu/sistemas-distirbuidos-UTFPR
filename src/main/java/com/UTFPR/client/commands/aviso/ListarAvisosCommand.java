package com.UTFPR.client.commands.aviso;

import com.UTFPR.domain.dto.OperacaoListarAvisosDTO;
import com.UTFPR.shared.commands.Command;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class ListarAvisosCommand implements Command {
    private PrintWriter out;
    private BufferedReader stdIn;
    private ObjectMapper objectMapper;
    private final String token;;

    public ListarAvisosCommand(PrintWriter out, BufferedReader stdIn, ObjectMapper objectMapper, String token) {
        this.out = out;
        this.stdIn = stdIn;
        this.objectMapper = objectMapper;
        this.token = token;
    }

    @Override
    public void execute() throws IOException {
        String id;
        do {
            System.out.println("Digite o ID (somente números): ");
            id = stdIn.readLine();
            if (!id.matches("^[0-9]+$")) {
                System.out.println("ID inválido. Digite novamente:");
            }
        } while (!id.matches("^[0-9]+$"));

        OperacaoListarAvisosDTO operacaoListarAvisosDTO = new OperacaoListarAvisosDTO("listarAvisos", token, Integer.parseInt(id));

        String json = objectMapper.writeValueAsString(operacaoListarAvisosDTO);
        System.out.println("Client: " + json);
        out.println(json);
    }
}
