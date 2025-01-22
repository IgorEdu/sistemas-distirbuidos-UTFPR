package com.UTFPR.client.commands;

import com.UTFPR.domain.dto.CategoriaDTO;
import com.UTFPR.domain.dto.OperacaoComTokenDTO;
import com.UTFPR.domain.dto.SalvarCategoriaDTO;
import com.UTFPR.shared.commands.Command;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class SalvarCategoriaCommand implements Command {
    private PrintWriter out;
    private BufferedReader stdIn;
    private ObjectMapper objectMapper;
    private final String token;

    public SalvarCategoriaCommand(PrintWriter out, BufferedReader stdIn, ObjectMapper objectMapper, String token) {
        this.out = out;
        this.stdIn = stdIn;
        this.objectMapper = objectMapper;
        this.token = token;
    }

    @Override
    public void execute() throws IOException {
        int idCategoria;
        String nomeCategoria;
        String temp;

        do {
            System.out.println("Digite o ID da categoria (Digite 0 para cadastrar uma categoria nova): ");
            temp = stdIn.readLine();

            if (!temp.matches("^[0-9]+$")) {
                System.out.println("ID inválido. Digite novamente:");
            }
            idCategoria = Integer.parseInt(temp);
        } while (!temp.matches("^[0-9]+$"));

        System.out.println("Digite o nome da categoria: ");
        nomeCategoria = stdIn.readLine();

        CategoriaDTO categoriaDTO = new CategoriaDTO(idCategoria, nomeCategoria);

        SalvarCategoriaDTO salvarCategoriaDTO = new SalvarCategoriaDTO("salvarCategoria", token, categoriaDTO);

        String json = objectMapper.writeValueAsString(salvarCategoriaDTO);
        System.out.println("Client: " + json);
        out.println(json);
    }
}
