package universite_paris8.iut.youadah.projet.vue;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import universite_paris8.iut.youadah.projet.modele.Inventaire;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import universite_paris8.iut.youadah.projet.modele.Objet;

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
        for (int i = 0; i <= 5; i++){
            cases.add(new ImageView(image));
            cases.get(i).setFitHeight(64);
            cases.get(i).setFitWidth(64);
            cases.get(i).setX((i*64)+730);
            pane.getChildren().add(cases.get(i));
        }
    }

    public void maj() {

        for (int i = 0; i < inventaire.getInventaire().size(); i++) {
            Objet objet = inventaire.getInventaire().get(i);
            ObjetVue objetVue = new ObjetVue(objet);
            ImageView iv = objetVue.getImageView();

            iv.setFitWidth(32); // optionnel
            iv.setFitHeight(32);
            iv.setLayoutX(((i) * 64) + 745);
            iv.setLayoutY(15); // si tes cases sont en haut du pane

            pane.getChildren().add(iv);
        }
    }
}
