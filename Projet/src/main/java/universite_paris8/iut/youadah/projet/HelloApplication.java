package universite_paris8.iut.youadah.projet;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

public class HelloApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/universite_paris8/iut/youadah/projet/hello-view.fxml"));

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        Scene scene = new Scene(fxmlLoader.load(),
                screenBounds.getWidth() * 0.8,
                screenBounds.getHeight() * 0.8);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Bilad al Sam");

        primaryStage.centerOnScreen();

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}