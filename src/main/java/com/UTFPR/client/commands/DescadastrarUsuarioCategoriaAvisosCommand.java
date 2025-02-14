package com.UTFPR.client.commands;

import com.UTFPR.domain.dto.CadastrarOuDescadastrarUsuarioCategoriaAvisosDTO;
import com.UTFPR.shared.commands.Command;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class DescadastrarUsuarioCategoriaAvisosCommand implements Command {
    private PrintWriter out;
    private BufferedReader stdIn;
    private ObjectMapper objectMapper;
    private final String token;

    public DescadastrarUsuarioCategoriaAvisosCommand(PrintWriter out, BufferedReader stdIn, ObjectMapper objectMapper, String token) {
        this.out = out;
        this.stdIn = stdIn;
        this.objectMapper = objectMapper;
        this.token = token;
    }

    @Override
    public void execute() throws IOException {
        int idCategoria;
        String ra;
        String temp;

        do {
            System.out.println("Digite o RA (somente números): ");
            ra = stdIn.readLine();
            if (!ra.matches("^[0-9]+$")) {
                System.out.println("RA inválido. Digite novamente:");
            }
        } while (!ra.matches("^[0-9]+$"));

        do {
            System.out.println("Digite o ID da categoria: ");
            temp = stdIn.readLine();

            if (!temp.matches("^[0-9]+$") || temp.equals("0")) {
                System.out.println("ID inválido. Digite novamente:");
            }
            idCategoria = Integer.parseInt(temp);
        } while (!temp.matches("^[0-9]+$") || temp.equals("0"));

        CadastrarOuDescadastrarUsuarioCategoriaAvisosDTO cadastrarUsuarioCategoriaAvisoDTO = new CadastrarOuDescadastrarUsuarioCategoriaAvisosDTO("descadastrarUsuarioCategoria", token, ra, idCategoria);

        String json = objectMapper.writeValueAsString(cadastrarUsuarioCategoriaAvisoDTO);
        System.out.println("Client: " + json);
        out.println(json);
    }
}
