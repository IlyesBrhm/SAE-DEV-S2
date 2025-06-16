package universite_paris8.iut.youadah.projet.vue;

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
        pane.getChildren().removeIf(node -> node instanceof ImageView || node instanceof Label);

        afficherInventaire(); // Réaffiche les cases

        for (int i = 0; i < inventaire.getInventaire().size(); i++) {
            Objet objet = inventaire.getInventaire().get(i);
            ObjetVue objetVue = new ObjetVue(objet);
            ImageView iv = objetVue.getImageView();

            iv.setFitWidth(32);
            iv.setFitHeight(32);
            iv.setLayoutX((i * 64) + 745);
            iv.setLayoutY(15);

            Label quantiteLabel = new Label("x" + objet.getQuantite());
            quantiteLabel.setTextFill(Color.WHITE);
            quantiteLabel.setFont(Font.font("Arial", javafx.scene.text.FontWeight.BOLD, 13));

            quantiteLabel.setStyle("-fx-background-color: rgba(0, 0, 0, 0.6); -fx-padding: 2px;");
            quantiteLabel.setLayoutX((i * 64) + 775);  // Plus bas à droite
            quantiteLabel.setLayoutY(45);

            pane.getChildren().addAll(iv, quantiteLabel);


        }
    }
}
