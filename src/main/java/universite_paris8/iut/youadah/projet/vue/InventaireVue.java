package universite_paris8.iut.youadah.projet.vue;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import universite_paris8.iut.youadah.projet.modele.Inventaire;
import universite_paris8.iut.youadah.projet.modele.Objet;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsable de l'affichage graphique de l'inventaire du joueur
 * Gère le rendu visuel des objets et leurs quantités dans l'interface utilisateur
 */
public class InventaireVue {
    /** Panneau JavaFX où l'inventaire sera affiché */
    private Pane pane;
    /** Modèle de l'inventaire contenant les objets */
    private Inventaire inventaire;
    /** Image représentant une case d'inventaire */
    private final Image image;

    /**
     * Constructeur de la vue d'inventaire
     * @param p Panneau où afficher l'inventaire
     * @param i Modèle d'inventaire à représenter
     */
    public InventaireVue(Pane p, Inventaire i) {
        pane = p;
        inventaire = i;
        image = new Image(getClass().getResource("/images/inventory.png").toExternalForm());
    }

    /**
     * Affiche les cases vides de l'inventaire
     * Crée 6 cases d'inventaire alignées horizontalement
     */
    public void afficherInventaire() {
        List<ImageView> cases = new ArrayList<>();
        for (int i = 0; i <= 5; i++) {
            ImageView caseView = new ImageView(image);
            caseView.setFitHeight(64);
            caseView.setFitWidth(64);
            caseView.setX((i * 64) + 730);
            pane.getChildren().add(caseView);
            cases.add(caseView);
        }
    }

    /**
     * Met à jour l'affichage de l'inventaire
     * Supprime les anciens éléments et affiche les objets actuels avec leurs quantités
     */
    public void maj() {
        // Supprimer les anciennes icônes/quantités
        pane.getChildren().removeIf(node -> node instanceof ImageView || node instanceof Label || node instanceof Group);

        afficherInventaire(); // Réaffiche les cases

        int slot = 0; // index visuel pour éviter les trous

        for (Objet objet : inventaire.getInventaire()) {
            if (objet.getQuantite() <= 0) continue; // Ne pas afficher les objets épuisés

            ObjetVue objetVue = new ObjetVue(objet);
            ImageView iv = objetVue.getImageView();

            // Configuration de l'image de l'objet
            iv.setFitWidth(32);
            iv.setFitHeight(32);
            iv.setLayoutX(0);
            iv.setLayoutY(0);

            // Création et configuration du label de quantité
            Label quantiteLabel = new Label("x" + objet.getQuantite());
            quantiteLabel.setTextFill(Color.WHITE);
            quantiteLabel.setFont(Font.font("Arial", javafx.scene.text.FontWeight.BOLD, 13));
            quantiteLabel.setStyle("-fx-background-color: rgba(0, 0, 0, 0.6); -fx-padding: 1px;");
            quantiteLabel.setLayoutX(20);
            quantiteLabel.setLayoutY(22);

            // Regroupement de l'image et du label dans un groupe
            Group group = new Group();
            group.getChildren().addAll(iv, quantiteLabel);
            group.setLayoutX((slot * 64) + 745);
            group.setLayoutY(15);

            pane.getChildren().add(group);
            slot++; // on n'incrémente que si un objet est visible
        }
    }

}
