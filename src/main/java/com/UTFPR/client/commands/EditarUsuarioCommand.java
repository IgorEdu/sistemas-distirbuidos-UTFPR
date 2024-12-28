package com.UTFPR.client.commands;

import com.UTFPR.domain.dto.EditaUsuarioDTO;
import com.UTFPR.domain.dto.SolicitaInformacoesUsuarioDTO;
import com.UTFPR.domain.entities.User;
import com.UTFPR.shared.commands.Command;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class EditarUsuarioCommand implements Command {
    private PrintWriter out;
    private BufferedReader stdIn;
    private ObjectMapper objectMapper;
    private final String token;

    public EditarUsuarioCommand(PrintWriter out, BufferedReader stdIn, ObjectMapper objectMapper, String token) {
        this.out = out;
        this.stdIn = stdIn;
        this.objectMapper = objectMapper;
        this.token = token;
    }

    @Override
    public void execute() throws IOException {
        String ra;
        String senha;
        String nome;

        do {
            System.out.println("Digite o RA (somente números): ");
            ra = stdIn.readLine();
            if (!ra.matches("^[0-9]+$")) {
                System.out.println("RA inválido. Digite novamente:");
            }
        } while (!ra.matches("^[0-9]+$"));
        do {
            System.out.println("Digite a senha: ");
            senha = stdIn.readLine();
            if (!senha.matches("^[a-zA-Z]+$")) {
                System.out.println("Senha no mínimo 8 caracteres, e no máx 20 caracteres, apenas letras, sem acentuação ou caracter especial.");
                System.out.println("Senha inválida. Digite novamente:");
            }
        } while(!senha.matches("^[a-zA-Z]+$"));

        do {
            System.out.println("Digite seu nome: ");
            nome = stdIn.readLine();
            if (!nome.matches("^[A-Z\\s]+$") || nome.length() > 50) {
                System.out.println("Nome no máximo 50 caracteres, apenas letras maiúsculas, sem acentuação ou caracter especial.");
                System.out.println("Nome inválido. Digite novamente:");
            }
        } while(!nome.matches("^[A-Z\\s]+$") || nome.length() > 50);

        User usuario = new User(ra, senha, nome);

        EditaUsuarioDTO editaUsuarioDTO = new EditaUsuarioDTO("editarUsuario", token, usuario);

        String json = objectMapper.writeValueAsString(editaUsuarioDTO);
        System.out.println("Client: " + json);
        out.println(json);
    }
}
