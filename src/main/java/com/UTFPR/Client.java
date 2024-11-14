package com.UTFPR;

import com.UTFPR.domain.dto.*;
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
        System.out.print ("input: ");
        while ((userInput = stdIn.readLine()) != null && token == null) {
            if(userInput.equals("1")) {
                System.out.println("Digite o RA (somente números): ");
                int ra = Integer.parseInt(stdIn.readLine());
                System.out.println("Digite a senha: ");
                String senha = stdIn.readLine();

                LoginDTO loginDTO = new LoginDTO("login", ra, senha);
                String json = objectMapper.writeValueAsString(loginDTO);
                System.out.println("Client: " + json);
                out.println(json);
            }

            if(userInput.equals("2")) {
                System.out.println("Digite o RA (somente números): ");
                int ra = Integer.parseInt(stdIn.readLine());
                System.out.println("Digite a senha: ");
                String senha = stdIn.readLine();
                System.out.println("Digite seu nome: ");
                String nome = stdIn.readLine();

                CadastroDTO cadastroDTO = new CadastroDTO("cadastrarUsuario", ra, senha, nome);
                String json = objectMapper.writeValueAsString(cadastroDTO);
                System.out.println("Client: " + json);
                out.println(json);
            }

            String responseServer = in.readLine();
            System.out.println("Server: " + responseServer);

            ResponseDTO responseDTO = objectMapper.readValue(responseServer, ResponseDTO.class);
            if(userInput.equals("1") && responseDTO.getStatus() == 200) {
                token = responseDTO.getToken();
                break;
            }

            System.out.println("Digite a operacao: ");
            System.out.println("\t1 - login");
            System.out.println("\t2 - cadastrar");
            System.out.print ("input: ");
        }

        if(token != null){
            System.out.println("Usuário conectado...");
            System.out.println();
            System.out.println("Digite a operacao: ");
            System.out.println("\t1 - escolher preferencias");
            System.out.println("\t0 - sair");
            System.out.print ("input: ");
        }


        while ((userInput = stdIn.readLine()) != null && token != null) {

            if(userInput.equals("1")) {
                System.out.println("Digite o RA (somente números): ");
                int ra = Integer.parseInt(stdIn.readLine());
            }

            if(userInput.equals("0")) {
                System.out.println("Realizando logout...");

                LogoutDTO logoutDTO = new LogoutDTO("logout", token);
                String json = objectMapper.writeValueAsString(logoutDTO);
                System.out.println("Client: " + json);
                out.println(json);

                String responseServer = in.readLine();
                System.out.println("Server: " + responseServer);

                token = null;
                break;
            }

            System.out.println("Digite a operacao: ");
            System.out.println("\t1 - escolher preferencias");
            System.out.println("\t0 - sair");
            System.out.print ("input: ");
        }

        out.close();
        in.close();
        stdIn.close();
        echoSocket.close();
    }
}
