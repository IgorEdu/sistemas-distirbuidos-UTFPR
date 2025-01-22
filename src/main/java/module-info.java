module UTFPR.sistemasdistribuido {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires com.fasterxml.jackson.databind;
    requires jakarta.persistence;
    requires jakarta.transaction;
    requires jakarta.cdi;
    requires java.sql;
    requires org.hibernate.orm.core;
    requires mysql.connector.j;

    exports com.UTFPR.domain.entities;
    exports com.UTFPR to javafx.graphics;
    exports com.UTFPR.domain.dto to com.fasterxml.jackson.databind;

    opens com.UTFPR.domain.entities to org.hibernate.orm.core, javafx.base;
    opens com.UTFPR.controller to javafx.fxml;
    opens com.UTFPR.domain.dto to com.fasterxml.jackson.databind;
}
