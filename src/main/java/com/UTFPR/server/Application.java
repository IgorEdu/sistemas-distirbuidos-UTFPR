package com.UTFPR.server;

import com.UTFPR.controller.ServerController;
import com.UTFPR.server.service.ServerService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws Exception {
        ServerService serverService = new ServerService();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/server-view.fxml"));
        Scene scene = new Scene(loader.load(), 500, 380);

        ServerController serverController = loader.getController();
        serverController.setServerService(serverService);


        stage.setTitle("Comunicação via socket - SERVIDOR");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() {
        ServerService serverService = new ServerService();
        serverService.stopServer();
        System.out.println("Aplicação encerrada.");
    }

    public static void main(String[] args) {
        launch();
    }
}
