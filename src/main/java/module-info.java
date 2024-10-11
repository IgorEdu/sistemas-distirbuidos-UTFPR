module com.sistemasdistribuido {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com to javafx.fxml;
    exports com.controller;
    opens com.controller to javafx.fxml;
    exports com;
}