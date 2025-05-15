module universite_paris8.iut.youadah.projet {
    requires javafx.controls;
    requires javafx.fxml;
    //requires javafx.web;

    requires org.controlsfx.controls;
    //requires com.dlsc.formsfx;
    //requires net.synedra.validatorfx;
    //requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    //requires eu.hansolo.tilesfx;
   // requires com.almasb.fxgl.all;
    requires java.desktop;
    requires javafx.graphics;

    opens universite_paris8.iut.youadah.projet to javafx.fxml;
    exports universite_paris8.iut.youadah.projet;
    exports universite_paris8.iut.youadah.projet.controller;
    opens universite_paris8.iut.youadah.projet.controller to javafx.fxml;
}