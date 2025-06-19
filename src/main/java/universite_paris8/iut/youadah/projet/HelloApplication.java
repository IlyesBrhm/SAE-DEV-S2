package universite_paris8.iut.youadah.projet;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Classe principale de l'application qui initialise et lance l'interface graphique du jeu.
 * Cette classe étend Application de JavaFX et sert de point d'entrée pour le jeu.
 */
public class HelloApplication extends Application {

    /**
     * Méthode appelée au démarrage de l'application JavaFX.
     * Configure et affiche la fenêtre principale du jeu.
     *
     * @param primaryStage La fenêtre principale de l'application
     * @throws Exception Si une erreur survient lors du chargement du fichier FXML
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Charge le fichier FXML qui définit l'interface utilisateur
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/universite_paris8/iut/youadah/projet/hello-view.fxml"));

        // Crée une scène avec le contenu chargé depuis le FXML et définit sa taille
        Scene scene = new Scene(fxmlLoader.load(), 1920, 1080);

        // Configure la fenêtre principale
        primaryStage.setScene(scene);
        primaryStage.setTitle("Bilad al Sam");  // Définit le titre de la fenêtre

        // Affiche la fenêtre
        primaryStage.show();
    }

    /**
     * Point d'entrée principal de l'application.
     * Lance l'application JavaFX en appelant la méthode launch.
     *
     * @param args Arguments de ligne de commande (non utilisés)
     */
    public static void main(String[] args) {
        launch(args);  // Démarre l'application JavaFX
    }
}
