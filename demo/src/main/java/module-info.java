module universite_paris8.iut.aclaudio.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens universite_paris8.iut.aclaudio.demo to javafx.fxml;
    exports universite_paris8.iut.aclaudio.demo;
    exports universite_paris8.iut.aclaudio.demo.world;
}