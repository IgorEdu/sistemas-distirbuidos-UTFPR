package com.UTFPR.controller;

import com.UTFPR.server.service.ServerService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

            // Obter o controlador da nova tela
            ServerOptionsController optionsController = loader.getController();
            optionsController.setPortaServidor(porta);

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

}
