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
        String serverIP;
        int serverPort;
        String token = null;

        if(args.length == 0) {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Digite o IP do servidor: ");
            serverIP = br.readLine();

            System.out.println("Digite a porta do servidor: ");
            serverPort = Integer.parseInt(br.readLine());
        } else{
            serverIP = args[0];
            serverPort = Integer.parseInt(args[1]);
//            token = args[2];
        }

        Socket serverSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            System.out.println("Conectado ao servidor " + serverIP + ":" + serverPort);

            serverSocket = new Socket(serverIP, serverPort);
            out = new PrintWriter(serverSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(
                    serverSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Servidor não encontrado: " + serverIP + ":" + serverPort);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for "
                    + "the connection to: " + serverIP + ":" + serverPort);
            System.exit(1);
        }

        ObjectMapper objectMapper = new ObjectMapper();

        BufferedReader stdIn = new BufferedReader(
                new InputStreamReader(System.in));
        String userInput;


        token = menuInicial(stdIn, token, in, out, serverSocket, objectMapper);

        System.out.println("Usuário conectado...");
        System.out.println();

        if (token != null) {
            apresentarMenu(token);
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
                case "4":
                    command = new ListarCategoriasCommand(out, stdIn, objectMapper, token);
                    command.execute();
                    break;
                case "5":
                    command = new LocalizarCategoriaCommand(out, stdIn, objectMapper, token);
                    command.execute();
                    break;
                case "a1":
                    command = new ListarUsuariosCommand(out, stdIn, objectMapper, token);
                    command.execute();
                    break;
                case "a2":
                    command = new SalvarCategoriaCommand(out, stdIn, objectMapper, token);
                    command.execute();
                    break;
                case "a3":
                    command = new ExcluirCategoriaCommand(out, stdIn, objectMapper, token);
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
        }

//        stdIn.close();
//        out.close();
//        in.close();
//        serverSocket.close();
//        encerrar(stdIn, out, in, serverSocket);
        token = menuInicial(stdIn, token, in, out, serverSocket, objectMapper);
    }


    private static void encerrar(BufferedReader stdIn, PrintWriter out, BufferedReader in,Socket echoSocket) throws IOException {
        System.out.println("Encerrando...");
        stdIn.close();
        out.close();
        in.close();
        echoSocket.close();
    }

    private static String menuInicial(BufferedReader stdIn,
                                    String token,
                                    BufferedReader in,
                                    PrintWriter out,
                                    Socket serverSocket,
                                    ObjectMapper objectMapper) throws IOException {
        String userInput;

        System.out.println("Digite a operacao: ");
        System.out.println("\t1 - login");
        System.out.println("\t2 - cadastrar");
        System.out.println("\t0 - sair");
        System.out.print("input: ");

        while ((userInput = stdIn.readLine()) != null && token == null) {
            Command command;
            switch (userInput) {
                case "0":
                    encerrar(stdIn, out, in, serverSocket);
                    return null;
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

        return token;
    }

    private static void apresentarMenu(String token){
        System.out.println("Digite a operacao: ");
        System.out.println("\t1 - localizar usuario");
        System.out.println("\t2 - excluir usuário");
        System.out.println("\t3 - editar usuário");
        System.out.println("\t4 - listar categorias");
        System.out.println("\t5 - localizar categoria");

        if(token.equals("9999999")){
            System.out.println("-----------------------------");
            System.out.println("FUNÇÕES DE ADMINISTRADOR");
            System.out.println("-----------------------------");
            System.out.println("\ta1 - listar usuarios");
            System.out.println("\ta2 - salvar categoria");
            System.out.println("\ta3 - excluir categoria");
            System.out.println("-----------------------------");
        }

        System.out.println("\t0 - sair");
        System.out.print("input: ");
    }
}
