package com.UTFPR.server;

import java.net.*;
import java.io.*;

import com.UTFPR.domain.dto.OperacaoDTO;
import com.UTFPR.server.commands.CommandInvoker;
import com.UTFPR.server.infra.AdminInitializer;
import com.UTFPR.server.infra.DatabaseConnection;
import com.UTFPR.server.repository.UserRepository;
import com.UTFPR.server.service.ResponseFormatter;
import com.UTFPR.server.service.ResponseService;
import com.UTFPR.server.service.UserService;
import com.UTFPR.shared.commands.Command;
import com.UTFPR.shared.commands.CommandFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;

public class Server extends Thread {
    protected Socket clientSocket;

    public static void main(String[] args) {
        int portServer = 20001;  // Porta padrão

        DatabaseConnection.init();

        try (ServerSocket serverSocket = configureServerSocket(args)) {
            System.out.println("Servidor iniciado na porta: " + portServer);

            try (EntityManager em = DatabaseConnection.getEntityManager()) {
                AdminInitializer.initializeAdmin(em);
            } catch (Exception e) {
                System.err.println("Erro ao inicializar administrador: " + e.getMessage());
                e.printStackTrace();
            }

            while (true) {
                System.out.println("Aguardando conexão...");
                new Server(serverSocket.accept());
            }
        } catch (IOException e) {
            System.err.println("Erro ao inicializar servidor: " + e.getMessage());
        } finally {
            DatabaseConnection.close();
        }
    }

    private Server(Socket clientSoc) {
        clientSocket = clientSoc;
        start();
    }

    private static ServerSocket configureServerSocket(String[] args) throws IOException {
        int portServer = 20001;  // Porta padrão

        if (args.length > 0) {
            portServer = Integer.parseInt(args[0]);
        } else {
            System.out.println("Digite a porta (ou pressione Enter para usar a porta padrão 20001): ");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String portInput = br.readLine();
            if (!portInput.isEmpty()) {
                portServer = Integer.parseInt(portInput);
            }
        }

        return new ServerSocket(portServer);
    }

    @Override
    public void run() {
        System.out.println("Nova Thread iniciada");
        System.out.println("Conexão aceita de " + clientSocket.getInetAddress() + "\n");

        try (EntityManager em = DatabaseConnection.getEntityManager()) {
            String inputLine;
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            while ((inputLine = in.readLine()) != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                OperacaoDTO operacaoDTO = objectMapper.readValue(inputLine, OperacaoDTO.class);
                UserRepository userRepository = new UserRepository(em);
                UserService userService = new UserService(userRepository);
                ResponseService responseService = new ResponseService();
                ResponseFormatter responseFormatter = new ResponseFormatter(new ObjectMapper());

                String clientAddress = String.valueOf(clientSocket.getInetAddress());

                CommandFactory commandFactory = new CommandFactory(userService, responseService, responseFormatter, out);
                Command command = commandFactory.createCommand(operacaoDTO, inputLine, clientAddress);
                CommandInvoker invoker = new CommandInvoker();
                invoker.executeCommand(command);
                if(operacaoDTO.getOperacao().equals("logout"))
                    break;
            }

            out.close();
            in.close();
            clientSocket.close();
        } catch (Exception e) {
            System.err.println("Erro durante comunicação com o cliente: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (clientSocket != null && !clientSocket.isClosed()) {
                    clientSocket.close();
                }
            } catch (IOException e) {
                System.err.println("Erro ao fechar o socket do cliente: " + e.getMessage());
            }
        }
    }
}
