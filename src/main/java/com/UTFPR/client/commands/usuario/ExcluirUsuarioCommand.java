package com.UTFPR.client.commands.usuario;

import com.UTFPR.domain.dto.SolicitaInformacoesUsuarioDTO;
import com.UTFPR.shared.commands.Command;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class ExcluirUsuarioCommand implements Command {
    private PrintWriter out;
    private BufferedReader stdIn;
    private ObjectMapper objectMapper;
    private String token;
    private String raExclusao;


    public ExcluirUsuarioCommand(PrintWriter out, BufferedReader stdIn, ObjectMapper objectMapper, String token) {
        this.out = out;
        this.stdIn = stdIn;
        this.objectMapper = objectMapper;
        this.token = token;
    }

    public String getRaExclusao(){
        return raExclusao;
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

        raExclusao = ra;

        SolicitaInformacoesUsuarioDTO solicitaInformacoesUsuarioDTO = new SolicitaInformacoesUsuarioDTO("excluirUsuario", token, ra);

        String json = objectMapper.writeValueAsString(solicitaInformacoesUsuarioDTO);
        System.out.println("Client: " + json);
        out.println(json);
    }
}
