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

/**
 * Classe principale de l'application : écran d'accueil du jeu.
 */
public class HelloApplication extends Application {

    private static final String CINZEL_PATH = "/fonts/Cinzel-Regular.ttf";
    private static final String TRAJAN_PRO_PATH = "/fonts/TrajanPro-Regular.ttf";

    @Override
    public void start(Stage primaryStage) {
        // Image de fond
        Image fond = new Image(getClass().getResource("/images/fond.png").toExternalForm());
        ImageView fondVue = new ImageView(fond);

        // Conteneur principal
        StackPane racine = new StackPane();
        fondVue.fitWidthProperty().bind(racine.widthProperty());
        fondVue.fitHeightProperty().bind(racine.heightProperty());

        // Boutons
        Button btnJouer = new Button("Nouvelle Partie");
        Button btnQuitter = new Button("Quitter");

        btnJouer.setPrefWidth(200);
        btnQuitter.setPrefWidth(200);

        // Styles de boutons
        String styleNormal = "-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 18px;";
        String styleHover = "-fx-background-color: transparent; -fx-text-fill: #cccccc; -fx-font-size: 18px;";

        btnJouer.setStyle(styleNormal);
        btnJouer.setOnMouseEntered(e -> {
            btnJouer.setStyle(styleHover);
            btnJouer.setScaleX(1.1);
            btnJouer.setScaleY(1.1);
        });
        btnJouer.setOnMouseExited(e -> {
            btnJouer.setStyle(styleNormal);
            btnJouer.setScaleX(1.0);
            btnJouer.setScaleY(1.0);
        });

        btnQuitter.setStyle(styleNormal);
        btnQuitter.setOnMouseEntered(e -> {
            btnQuitter.setStyle(styleHover);
            btnQuitter.setScaleX(1.1);
            btnQuitter.setScaleY(1.1);
        });
        btnQuitter.setOnMouseExited(e -> {
            btnQuitter.setStyle(styleNormal);
            btnQuitter.setScaleX(1.0);
            btnQuitter.setScaleY(1.0);
        });

        // Action du bouton jouer
        btnJouer.setOnAction(e -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/universite_paris8/iut/youadah/projet/hello-view.fxml"));
                Scene sceneJeu = new Scene(fxmlLoader.load(), 1680, 1050);
                primaryStage.setScene(sceneJeu);
                primaryStage.setTitle("Bilad al Sam - Jeu");
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("Erreur de chargement du jeu.");
            }
        });

        // Action bouton quitter
        btnQuitter.setOnAction(e -> System.exit(0));

        // Disposition verticale des boutons
        VBox boxBoutons = new VBox(20, btnJouer, btnQuitter);
        boxBoutons.setAlignment(Pos.CENTER);

        racine.getChildren().addAll(fondVue, boxBoutons);

        // Lancement de la scène d’accueil
        Scene accueil = new Scene(racine, 1920, 1080);
        primaryStage.setTitle("Bilad al Sam - Accueil");
        primaryStage.setScene(accueil);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
