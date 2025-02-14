package com.UTFPR.client;

import com.UTFPR.client.commands.*;
import com.UTFPR.client.commands.aviso.ExcluirAvisoCommand;
import com.UTFPR.client.commands.aviso.ListarAvisosCommand;
import com.UTFPR.client.commands.aviso.LocalizarAvisoCommand;
import com.UTFPR.client.commands.aviso.SalvarAvisoCommand;
import com.UTFPR.client.commands.categoria.ExcluirCategoriaCommand;
import com.UTFPR.client.commands.categoria.ListarCategoriasCommand;
import com.UTFPR.client.commands.categoria.LocalizarCategoriaCommand;
import com.UTFPR.client.commands.categoria.SalvarCategoriaCommand;
import com.UTFPR.client.commands.usuario.*;
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

        if (args.length == 0) {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("Digite o IP do servidor: ");
            serverIP = br.readLine();

            System.out.println("Digite a porta do servidor: ");
            serverPort = Integer.parseInt(br.readLine());
        } else {
            serverIP = args[0];
            serverPort = Integer.parseInt(args[1]);
            token = args[2];
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

        String raUsuarioExcluido = null;

        do {
            token = menuInicial(stdIn, token, in, out, serverSocket, objectMapper);

            if (token == null) {
                break;
            }

            System.out.println("Usuário conectado...");
            System.out.println();
            buscaListagemDeAvisosDoUsuario(out, stdIn, objectMapper, token);
            apresentarMenu(token);


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
                        ExcluirUsuarioCommand excluirUsuarioCommand = new ExcluirUsuarioCommand(out, stdIn, objectMapper, token);
                        excluirUsuarioCommand.execute();
                        raUsuarioExcluido = excluirUsuarioCommand.getRaExclusao();
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
                    case "6":
                        command = new ListarAvisosCommand(out, stdIn, objectMapper, token);
                        command.execute();
                        break;
                    case "7":
                        command = new LocalizarAvisoCommand(out, stdIn, objectMapper, token);
                        command.execute();
                        break;
                    case "8":
                        command = new CadastrarUsuarioCategoriaAvisosCommand(out, stdIn, objectMapper, token);
                        command.execute();
                        break;
                    case "9":
                        command = new DescadastrarUsuarioCategoriaAvisosCommand(out, stdIn, objectMapper, token);
                        command.execute();
                        break;
                    case "10":
                        command = new ListarAvisosUsuarioCommand(out, stdIn, objectMapper, token);
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
                    case "a4":
                        command = new SalvarAvisoCommand(out, stdIn, objectMapper, token);
                        command.execute();
                        break;
                    case "a5":
                        command = new ExcluirAvisoCommand(out, stdIn, objectMapper, token);
                        command.execute();
                        break;
                    default:
                        System.out.println("Operação inválida.");
                }

                String responseServer = in.readLine();
                System.out.println("Server: " + responseServer);

                ResponseDTO responseDTO = objectMapper.readValue(responseServer, ResponseDTO.class);
                if(responseDTO.getStatus() == 201 && responseDTO.getOperacao().equals("excluirUsuario") && raUsuarioExcluido.equals(token)){
                    token = null;
                }

                if (token == null) {
                    break;
                }

                apresentarMenu(token);
            }


//        stdIn.close();
//        out.close();
//        in.close();
//        serverSocket.close();
//        encerrar(stdIn, out, in, serverSocket);
        } while (token == null);
    }


    private static void encerrar(BufferedReader stdIn, PrintWriter out, BufferedReader in, Socket echoSocket) throws IOException {
        System.out.println("Encerrando...");
        stdIn.close();
        out.close();
//        in.close();
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

    private static void buscaListagemDeAvisosDoUsuario(PrintWriter out, BufferedReader stdIn, ObjectMapper objectMapper, String token) throws IOException {
        Command command = new ListarAvisosUsuarioInicialCommand(out, stdIn, objectMapper, token);
        command.execute();

        String responseServer = stdIn.readLine();
        System.out.println("Server: " + responseServer);
    }

    private static void apresentarMenu(String token) {
        System.out.println("Digite a operacao: ");
        System.out.println("\t1 - localizar usuario");
        System.out.println("\t2 - excluir usuário");
        System.out.println("\t3 - editar usuário");
        System.out.println("\t4 - listar categorias");
        System.out.println("\t5 - localizar categoria");
        System.out.println("\t6 - listar avisos");
        System.out.println("\t7 - localizar aviso");
        System.out.println("\t8 - cadastrar usuario em categoria de avisos");
        System.out.println("\t9 - descadastrar usuario em categoria de avisos");
        System.out.println("\t10 - listar avisos do usuario");

        if (token.equals("9999999")) {
            System.out.println("-----------------------------");
            System.out.println("FUNÇÕES DE ADMINISTRADOR");
            System.out.println("-----------------------------");
            System.out.println("\ta1 - listar usuarios");
            System.out.println("\ta2 - salvar categoria");
            System.out.println("\ta3 - excluir categoria");
            System.out.println("\ta4 - salvar aviso");
            System.out.println("\ta5 - excluir aviso");
            System.out.println("-----------------------------");
        }

        System.out.println("\t0 - sair");
        System.out.print("input: ");
    }
}
