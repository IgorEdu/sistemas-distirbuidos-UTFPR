package com.UTFPR.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class ServerOptionsController {

    @FXML
    private Label lblPortaServidor;

    @FXML
    private static ListView<String> listViewUsuarios;

    public void setPortaServidor(int porta) {
        lblPortaServidor.setText("Servidor iniciado na porta: " + porta);
    }

    public static void atualizarListaUsuarios(String usuario) {
        listViewUsuarios.getItems().add(usuario);
    }

    public static void removerUsuario(String usuario) {
        listViewUsuarios.getItems().remove(usuario);
    }
}
