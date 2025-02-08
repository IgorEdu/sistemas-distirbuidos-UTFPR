package com.UTFPR.controller;

import com.UTFPR.server.infra.DatabaseConnection;
import com.UTFPR.server.repository.UserRepository;
import com.UTFPR.server.service.ServerService;
import com.UTFPR.server.service.UserService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ServerController {
    @FXML
    private Label lblDigitePortaServidor;

    @FXML
    private TextField edtPortaServidor;

    @FXML
    private Button btnIniciarServidor;

    private ServerService serverService;

    public ServerController() {
        this.serverService = new ServerService();
//        this.usuariosLogados = new ArrayList<>();
    }

    @FXML
    private void iniciarServidor() {
        try {
            int porta = Integer.parseInt(edtPortaServidor.getText());
            serverService.setPort(porta);
            serverService.startServer();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/optionsServer.fxml"));
            Parent nextSceneRoot = loader.load();

            startClient(porta);

            ServerOptionsController controller = loader.getController();

            // Inicializa o UserService no controlador
            controller.setUserService(new UserService(new UserRepository(DatabaseConnection.getEntityManager())));

            // Obter o controlador da nova tela
            ServerOptionsController optionsController = loader.getController();
            optionsController.setPortaServidor(porta);

            serverService.setServerOptionsController(optionsController);

            // Alterar a cena
            Stage stage = (Stage) btnIniciarServidor.getScene().getWindow();
            Scene nextScene = new Scene(nextSceneRoot);
            stage.setScene(nextScene);

        } catch (NumberFormatException e) {
            lblDigitePortaServidor.setText("Porta inválida!");
        } catch (IOException e) {
            lblDigitePortaServidor.setText("Erro ao iniciar o servidor.");
            e.printStackTrace();
        }
    }

    @FXML
    private void pararServidor() {
        serverService.stopServer();
        lblDigitePortaServidor.setText("Servidor parado.");
    }

    public void setServerService(ServerService serverService) {
        this.serverService = serverService;
    }

    private void startClient(int port) {
        Thread clientThread = new Thread(() -> {
            try {
                String serverIP = "127.0.0.1";
                int serverPort = port;
                String token = "9999999";

                com.UTFPR.client.Client.main(new String[]{serverIP, String.valueOf(serverPort), token});
            } catch (Exception e) {
                System.err.println("Erro ao iniciar o cliente: " + e.getMessage());
                e.printStackTrace();
            }
        });

        clientThread.setDaemon(true); // Define a thread como daemon para encerrar com a aplicação
        clientThread.start();
    }

}
