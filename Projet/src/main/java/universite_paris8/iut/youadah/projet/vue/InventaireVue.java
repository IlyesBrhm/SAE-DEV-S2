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

public class InventaireVue {
    private Pane pane;
    private Inventaire inventaire;
    private final Image image;

    public InventaireVue(Pane p, Inventaire i) {
        pane = p;
        inventaire = i;
        image = new Image(getClass().getResource("/images/inventory.png").toExternalForm());
    }

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

    public void maj() {
        // Supprimer les anciennes icônes/quantités
        pane.getChildren().removeIf(node -> node instanceof ImageView || node instanceof Label || node instanceof Group);

        afficherInventaire(); // Réaffiche les cases

        int slot = 0; // index visuel pour éviter les trous

        for (Objet objet : inventaire.getInventaire()) {
            if (objet.getQuantite() <= 0) continue; // Ne pas afficher les objets épuisés

            ObjetVue objetVue = new ObjetVue(objet);
            ImageView iv = objetVue.getImageView();

            iv.setFitWidth(32);
            iv.setFitHeight(32);
            iv.setLayoutX(0);
            iv.setLayoutY(0);

            Label quantiteLabel = new Label("x" + objet.getQuantite());
            quantiteLabel.setTextFill(Color.WHITE);
            quantiteLabel.setFont(Font.font("Arial", javafx.scene.text.FontWeight.BOLD, 13));
            quantiteLabel.setStyle("-fx-background-color: rgba(0, 0, 0, 0.6); -fx-padding: 1px;");
            quantiteLabel.setLayoutX(20);
            quantiteLabel.setLayoutY(22);

            Group group = new Group();
            group.getChildren().addAll(iv, quantiteLabel);
            group.setLayoutX((slot * 64) + 745);
            group.setLayoutY(15);

            pane.getChildren().add(group);
            slot++; // on n'incrémente que si un objet est visible
        }
    }

}
