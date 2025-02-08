package com.UTFPR.controller;

import com.UTFPR.domain.entities.User;
import com.UTFPR.server.service.UserService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ServerOptionsController {

    @FXML
    private Label lblPortaServidor;

    @FXML
    private ListView<String> listViewUsuarios;

    @FXML
    private TableView<User> tableViewUsuariosCadastrados;

    @FXML
    private void onListarUsuarios() {
        listarUsuarios();
    }


    private UserService userService;

    public void setPortaServidor(int porta) {
        lblPortaServidor.setText(String.valueOf(porta));
    }

    public void adicionarUsuarioConectado(String usuario) {
        Platform.runLater(() -> listViewUsuarios.getItems().add(usuario));
    }

    public void removerUsuario(String usuario) {
        Platform.runLater(() -> listViewUsuarios.getItems().remove(usuario));
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @FXML
    private TableColumn<User, String> colunaRa;

    @FXML
    private TableColumn<User, String> colunaNome;


    @FXML
    private void initialize() {
        colunaRa.setCellValueFactory(new PropertyValueFactory<>("ra"));
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
    }


    private void listarUsuarios() {
        ObservableList<User> usuarios = FXCollections.observableArrayList();

        if(userService != null){
            usuarios.addAll(userService.getAllUsers());
            tableViewUsuariosCadastrados.setItems(usuarios);
        }
    }
}
