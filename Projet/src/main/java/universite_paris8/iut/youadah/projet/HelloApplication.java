package universite_paris8.iut.youadah.projet;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1680, 1050);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Bilad al Sam");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
