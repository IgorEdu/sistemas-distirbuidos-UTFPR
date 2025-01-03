package com.UTFPR.client;

import com.UTFPR.client.commands.*;
import com.UTFPR.domain.dto.*;
import com.UTFPR.shared.commands.Command;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Digite o IP do servidor: ");
        String serverIP = br.readLine();

        System.out.println("Digite a porta do servidor: ");
        int serverPort = Integer.parseInt(br.readLine());

        Socket echoSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        String token = null;

        try {
            System.out.println("Conectado ao servidor " + serverIP + ":" + serverPort);
//            System.out.println("Digite 'Bye.' para encerrar");
            echoSocket = new Socket(serverIP, serverPort);
            out = new PrintWriter(echoSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(
                    echoSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Servidor não encontrado: " + serverIP + ":" + serverPort);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for "
                    + "the connection to: " + serverIP + ":" + serverPort);
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(
                new InputStreamReader(System.in));
        String userInput;

        ObjectMapper objectMapper = new ObjectMapper();

        System.out.println("Digite a operacao: ");
        System.out.println("\t1 - login");
        System.out.println("\t2 - cadastrar");
        System.out.println("\t0 - sair");
        System.out.print("input: ");
        while ((userInput = stdIn.readLine()) != null && token == null) {
            Command command;
            switch (userInput) {
                case "0":
                    encerrar(stdIn, out, in, echoSocket);
                    return;
                case "1":
                    command = new LoginCommand(out, stdIn, objectMapper);
                    command.execute();
                    break;
                case "2":
                    command = new CadastroCommand(out, stdIn, objectMapper);
                    command.execute();
                    break;
                default:
                    System.out.println("Operação inválida.");
                    continue;
            }

            String responseServer = in.readLine();
            System.out.println("Server: " + responseServer);

            ResponseDTO responseDTO = objectMapper.readValue(responseServer, ResponseDTO.class);
            if (userInput.equals("1") && responseDTO.getStatus() == 200) {
                token = responseDTO.getToken();
                break;
            }

            System.out.println("Digite a operacao: ");
            System.out.println("\t1 - login");
            System.out.println("\t2 - cadastrar");
            System.out.println("\t0 - sair");
            System.out.print("input: ");
        }

        if (token != null) {
            System.out.println("Usuário conectado...");
            System.out.println();
            System.out.println("Digite a operacao: ");
            System.out.println("\t1 - localizar usuario");
            System.out.println("\t2 - excluir usuário");
            System.out.println("\t3 - editar usuário");
            System.out.println("\t0 - sair");
            System.out.print("input: ");
        }


        while ((userInput = stdIn.readLine()) != null && token != null) {
            Command command;
            switch (userInput) {
                case "0":
                    command = new LogoutCommand(out, objectMapper, token);
                    command.execute();
                    token = null;
                    break;
                case "1":
                    command = new LocalizarUsuarioCommand(out, stdIn, objectMapper, token);
                    command.execute();
                    break;
                case "2":
                    command = new ExcluirUsuarioCommand(out, stdIn, objectMapper, token);
                    command.execute();
                    break;
                case "3":
                    command = new EditarUsuarioCommand(out, stdIn, objectMapper, token);
                    command.execute();
                    break;
                default:
                    System.out.println("Operação inválida.");
            }

            String responseServer = in.readLine();
            System.out.println("Server: " + responseServer);

            if (token == null) {
                break;
            }

            System.out.println("Digite a operacao: ");
            System.out.println("\t1 - localizar usuário");
            System.out.println("\t2 - excluir usuário");
            System.out.println("\t3 - editar usuário");
            System.out.println("\t0 - sair");
            System.out.print("input: ");
        }

//        stdIn.close();
//        out.close();
//        in.close();
//        echoSocket.close();
        encerrar(stdIn, out, in, echoSocket);
    }


    private static void encerrar(BufferedReader stdIn, PrintWriter out, BufferedReader in,Socket echoSocket) throws IOException {
        System.out.println("Encerrando...");
        stdIn.close();
        out.close();
        in.close();
        echoSocket.close();
    }
}
