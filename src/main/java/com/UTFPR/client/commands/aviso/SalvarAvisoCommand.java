package com.UTFPR.client.commands.aviso;

import com.UTFPR.domain.dto.AvisoDTO;
import com.UTFPR.domain.dto.SalvarAvisoDTO;
import com.UTFPR.shared.commands.Command;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class SalvarAvisoCommand implements Command {
    private PrintWriter out;
    private BufferedReader stdIn;
    private ObjectMapper objectMapper;
    private final String token;

    public SalvarAvisoCommand(PrintWriter out, BufferedReader stdIn, ObjectMapper objectMapper, String token) {
        this.out = out;
        this.stdIn = stdIn;
        this.objectMapper = objectMapper;
        this.token = token;
    }

    @Override
    public void execute() throws IOException {
        int idAviso;
        int idCategoria;
        String titulo;
        String descricao;

        String temp;

        do {
            System.out.println("Digite o ID do aviso (Digite 0 para cadastrar um aviso novo): ");
            temp = stdIn.readLine();

            if (!temp.matches("^[0-9]+$")) {
                System.out.println("ID inválido. Digite novamente:");
            }
            idAviso = Integer.parseInt(temp);
        } while (!temp.matches("^[0-9]+$"));

        do {
            System.out.println("Digite o ID da categoria: ");
            temp = stdIn.readLine();

            if (!temp.matches("^[0-9]+$") && temp.equals("0")) {
                System.out.println("Categoria inválida. Digite novamente:");
            }
            idCategoria = Integer.parseInt(temp);
        } while (!temp.matches("^[0-9]+$") && temp.equals("0"));

        System.out.println("Digite o título do aviso: ");
        titulo = stdIn.readLine();

        System.out.println("Digite a descrição do aviso: ");
        descricao = stdIn.readLine();

        AvisoDTO avisoDTO = new AvisoDTO(idAviso, idCategoria, titulo, descricao);

        SalvarAvisoDTO salvarAvisoDTO = new SalvarAvisoDTO("salvarAviso", token, avisoDTO);

        String json = objectMapper.writeValueAsString(salvarAvisoDTO);
        System.out.println("Client: " + json);
        out.println(json);
    }
}
