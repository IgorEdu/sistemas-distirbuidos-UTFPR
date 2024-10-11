package com.controller;

import com.models.Client;
import com.models.Server;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Controller {
    @FXML
    private Label lblConvertedText;

    @FXML
    private TextField txtMensagemConverter;

    @FXML
    protected void onHelloButtonClick() {
        String convertedText;

        Client client = new Client();
        Server server = new Server();

        client.setText(txtMensagemConverter.getText());
        convertedText = server.convertText(client.getText());

        lblConvertedText.setText(convertedText);

    }
}