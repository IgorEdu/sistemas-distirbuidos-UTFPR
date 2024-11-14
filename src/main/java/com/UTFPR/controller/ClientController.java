package com.UTFPR.controller;

import com.UTFPR.old.models.Client;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ClientController {
    @FXML
    private Label lblConvertedText;

    @FXML
    private TextField txtMensagemConverter;

    Client cliente = new Client();

    @FXML
    protected void onHelloButtonClick() {
        txtMensagemConverter.setText(cliente.getText());
    }
}