package com.UTFPR.client.commands;

import com.UTFPR.domain.dto.LoginDTO;
import com.UTFPR.shared.commands.Command;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginCommand implements Command {
    private PrintWriter out;
    private BufferedReader stdIn;
    private ObjectMapper objectMapper;
    private String token;

    public LoginCommand(PrintWriter out, BufferedReader stdIn, ObjectMapper objectMapper) {
        this.out = out;
        this.stdIn = stdIn;
        this.objectMapper = objectMapper;
    }

    @Override
    public void execute() throws IOException {
        String ra;
        do {
            System.out.println("Digite o RA (somente números): ");
            ra = stdIn.readLine();
            if (!ra.matches("^[0-9]+$")) {
                System.out.println("RA inválido. Digite novamente:");
            }
        } while (!ra.matches("^[0-9]+$"));
        System.out.println("Digite a senha: ");
        String senha = stdIn.readLine();

        LoginDTO loginDTO = new LoginDTO("login", ra, senha);
        String json = objectMapper.writeValueAsString(loginDTO);
        System.out.println("Client: " + json);
        out.println(json);
    }
}
