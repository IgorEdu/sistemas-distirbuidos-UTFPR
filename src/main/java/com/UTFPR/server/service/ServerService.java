package com.UTFPR.server.service;

import com.UTFPR.controller.ServerOptionsController;
import com.UTFPR.domain.UsuarioAtivo;
import com.UTFPR.domain.dto.OperacaoDTO;
import com.UTFPR.server.commands.CommandInvoker;
import com.UTFPR.server.infra.AdminInitializer;
import com.UTFPR.server.infra.DatabaseConnection;
import com.UTFPR.server.repository.UserRepository;
import com.UTFPR.shared.commands.CommandFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerService {
    private int port = 20001; // Porta padrão
    private ServerSocket serverSocket;
    private boolean adminInitialized = false;
    private Thread serverThread;

    private ServerOptionsController serverOptionsController;
    private UserSessionService userSessionService;


    public ServerService() {
        DatabaseConnection.init();
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    public void setServerOptionsController(ServerOptionsController controller) {
        this.serverOptionsController = controller;
        this.userSessionService = new UserSessionService(serverOptionsController);
    }

    public void startServer() throws IOException {
        if (serverSocket != null && !serverSocket.isClosed()) {
            throw new IllegalStateException("Servidor já está em execução.");
        }

        // Inicializar administrador apenas uma vez
        if (!adminInitialized) {
            try (EntityManager em = DatabaseConnection.getEntityManager()) {
                AdminInitializer.initializeAdmin(em);
                adminInitialized = true; // Marcar como inicializado
                System.out.println("Administrador configurado.");
            } catch (Exception e) {
                System.err.println("Erro ao inicializar administrador: " + e.getMessage());
                e.printStackTrace();
            }
        }

        serverSocket = new ServerSocket(port);
        System.out.println("Servidor iniciado na porta " + port);

        // Iniciar thread principal para aceitar conexões
        serverThread = new Thread(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    System.out.println("Aguardando conexões na porta " + port);
                    Socket clientSocket = serverSocket.accept();
                    handleClient(clientSocket); // Lidar com o cliente conectado
                }
            } catch (IOException e) {
                System.err.println("Erro no servidor: " + e.getMessage());
            }
        });

        serverThread.setDaemon(true);
        serverThread.start();
    }

    private void handleClient(Socket clientSocket) {
        new Thread(() -> {
            System.out.println("Nova conexão aceita de " + clientSocket.getInetAddress());

            String socketCliente = clientSocket.getInetAddress().toString();

            String ipCliente = clientSocket.getInetAddress().toString();
            String nomeUsuario = "Desconhecido";
            String raUsuario = "Desconhecido";

            this.userSessionService.adicionarUsuarioAtivo(nomeUsuario, raUsuario, ipCliente, socketCliente);

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/server-view.fxml"));
                Parent root = loader.load();

                try (EntityManager em = DatabaseConnection.getEntityManager();
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        ObjectMapper objectMapper = new ObjectMapper();
                        OperacaoDTO operacaoDTO = objectMapper.readValue(inputLine, OperacaoDTO.class);
                        UserRepository userRepository = new UserRepository(em);
                        UserService userService = new UserService(userRepository);

                        ResponseService responseService = new ResponseService();
                        ResponseFormatter responseFormatter = new ResponseFormatter(objectMapper);

                        CommandInvoker invoker = new CommandInvoker();
                        invoker.executeCommand(
                                new CommandFactory(userService, responseService, responseFormatter, out)
                                        .createCommand(operacaoDTO, inputLine, clientSocket.getInetAddress().toString())
                        );
                    }
                } catch (Exception e) {
                    System.err.println("Erro durante comunicação com o cliente: " + e.getMessage());
                } finally {
                    userSessionService.removerUsuarioAtivo();
                }
            } catch (Exception e) {
                System.err.println("Erro ao carregar o FXML ou configurar o controlador: " + e.getMessage());
            }
        }).start();
    }

    public void stopServer() {
        if (serverSocket != null && !serverSocket.isClosed()) {
            try {
                serverSocket.close();
                System.out.println("Servidor parado.");
            } catch (IOException e) {
                System.err.println("Erro ao fechar o servidor: " + e.getMessage());
            }
        }
    }
}
