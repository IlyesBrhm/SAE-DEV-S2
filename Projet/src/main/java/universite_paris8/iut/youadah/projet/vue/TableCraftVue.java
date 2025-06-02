
package universite_paris8.iut.youadah.projet.vue;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import universite_paris8.iut.youadah.projet.modele.*;

public class TableCraftVue {
    private final Pane pane;
    private final TableCraft tableCraft;
    private final Inventaire inventaire;
    private final InventaireVue inventaireVue;
    private Pane ath;

    public TableCraftVue(Pane pane, TableCraft tableCraft, Inventaire inventaire, InventaireVue inventaireVue, Pane ath) {
        this.pane = pane;
        this.tableCraft = tableCraft;
        this.inventaire = inventaire;
        this.inventaireVue = inventaireVue;
        this.ath = ath;
    }

    public void afficher() {
        pane.getChildren().clear();
        VBox layout = new VBox(10);
        layout.setLayoutX(10);
        layout.setLayoutY(10);

        for (Recette recette : tableCraft.getRecettes()) {
            if (!recette.peutEtreFabriqueAvec(inventaire.getInventaire())) continue;

            HBox ligneRecette = new HBox(10);

            for (Objet composant : recette.getComposants()) {
                ImageView img = new ObjetVue(composant).getImageView();
                img.setFitWidth(32);
                img.setFitHeight(32);
                ligneRecette.getChildren().add(img);
            }

            Label fleche = new Label("→");
            fleche.setStyle("-fx-font-size: 20px; -fx-padding: 5px;");
            ligneRecette.getChildren().add(fleche);

            ImageView resultat = new ObjetVue(recette.getResultat()).getImageView();
            resultat.setFitWidth(32);
            resultat.setFitHeight(32);
            ligneRecette.getChildren().add(resultat);

            Button bouton = new Button("Craft");
            bouton.setOnAction(e -> {
                Objet o = tableCraft.crafter(recette, inventaire);
                if (o != null) {
                    System.out.println("Créé : " + o.getNom());
                    ath.getChildren().clear();
                    inventaireVue.afficherInventaire();
                    inventaireVue.maj();
                    afficher();
                }
            });
            ligneRecette.getChildren().add(bouton);

            layout.getChildren().add(ligneRecette);
        }

        pane.getChildren().add(layout);
    }
    public Pane getPane() {
        return this.pane; // ou le nom réel du champ si c’est différent
    }

}
