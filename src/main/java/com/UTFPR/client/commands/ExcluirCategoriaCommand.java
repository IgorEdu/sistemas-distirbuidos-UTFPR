package com.UTFPR.client.commands;

import com.UTFPR.domain.dto.SolicitaInformacoesCategoriaDTO;
import com.UTFPR.domain.dto.SolicitaInformacoesUsuarioDTO;
import com.UTFPR.shared.commands.Command;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class ExcluirCategoriaCommand implements Command {
    private PrintWriter out;
    private BufferedReader stdIn;
    private ObjectMapper objectMapper;
    private final String token;

    public ExcluirCategoriaCommand(PrintWriter out, BufferedReader stdIn, ObjectMapper objectMapper, String token) {
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
        } while (!id.matches("^[0-9]+$") || id.equals("0"));

        SolicitaInformacoesCategoriaDTO solicitaInformacoesCategoriaDTO = new SolicitaInformacoesCategoriaDTO("excluirCategoria", token, id);

        String json = objectMapper.writeValueAsString(solicitaInformacoesCategoriaDTO);
        System.out.println("Client: " + json);
        out.println(json);
    }
}
