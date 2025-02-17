package com.UTFPR.client;

import com.UTFPR.client.service.ClientService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws Exception {
        ClientService clientService = new ClientService();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/client-view.fxml"));
        Scene scene = new Scene(loader.load(), 500, 380);

        ClientController clientController = loader.getController();
        clientController.setClientService(clientService);

        stage.setTitle("Comunicação via socket - CLIENTE");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() {
        ClientService clientService = new ClientService();
        clientService.stopServer();
        System.out.println("Aplicação encerrada.");
    }

    public static void main(String[] args) {
        launch();
    }
}
