package universite_paris8.iut.youadah.projet;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Pos;

public class HelloApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        // charger l'image de fond
        Image backgroundImage = new Image(getClass().getResource("/images/fond.png").toExternalForm());
        ImageView backgroundView = new ImageView(backgroundImage);
        backgroundView.setPreserveRatio(false); // Permet l'étirement complet

        // créer la racine
        StackPane root = new StackPane();

        // lier les dimensions de l'image à celles de la fenêtre
        backgroundView.fitWidthProperty().bind(root.widthProperty());
        backgroundView.fitHeightProperty().bind(root.heightProperty());

        // créer les boutons
        Button nouvellePartie = new Button("Nouvelle Partie");
        Button quitter = new Button("Quitter");

        nouvellePartie.setPrefWidth(200);
        quitter.setPrefWidth(200);

        //design des boutons
        nouvellePartie.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 18px;-fx-font-family: 'Merriweather';");
        nouvellePartie.setOnMouseEntered(e -> {
            nouvellePartie.setScaleX(1.1);
            nouvellePartie.setScaleY(1.1);
            nouvellePartie.setStyle("-fx-background-color: transparent; -fx-text-fill: #cccccc; -fx-font-size: 18px;-fx-font-family: 'Merriweather';");
        });
        nouvellePartie.setOnMouseExited(e -> {
            nouvellePartie.setScaleX(1.0);
            nouvellePartie.setScaleY(1.0);
            nouvellePartie.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 18px;-fx-font-family: 'Merriweather';");
        });

        quitter.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 18px; -fx-font-family: 'Merriweather';");
        quitter.setOnMouseEntered(e -> {
            quitter.setScaleX(1.1);
            quitter.setScaleY(1.1);
            quitter.setStyle("-fx-background-color: transparent; -fx-text-fill: #cccccc; -fx-font-size: 18px;-fx-font-family: 'Merriweather';");
        });
        quitter.setOnMouseExited(e -> {
            quitter.setScaleX(1.0);
            quitter.setScaleY(1.0);
            quitter.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 18px;-fx-font-family: 'Merriweather';");
        });



        // action bouton "Nouvelle Partie"
        nouvellePartie.setOnAction(e -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
                if (fxmlLoader.getLocation() == null) {
                    System.out.println("fichier FXML non trouvé");
                    return;
                }
                Scene gameScene = new Scene(fxmlLoader.load(), 1680, 1050);
                primaryStage.setScene(gameScene);
                primaryStage.setTitle("Bilad al Sam - Jeu");
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("erreur lors du chargement de vue1.fxml");
            }
        });

        // action bouton "Quitter"
        quitter.setOnAction(e -> System.exit(0));

        // layout des boutons
        VBox buttonBox = new VBox(20, nouvellePartie, quitter);
        buttonBox.setAlignment(Pos.CENTER);

        // superposer l'image et les boutons
        root.getChildren().addAll(backgroundView, buttonBox);

        // scène d'accueil
        Scene accueilScene = new Scene(root, 1680, 1050);
        primaryStage.setTitle("Bilad al Sam - Accueil");
        primaryStage.setScene(accueilScene);
        primaryStage.show();


    }

    public static void main(String[] args) {
        launch(args);
    }

}