package universite_paris8.iut.youadah.projet.vue;

import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import universite_paris8.iut.youadah.projet.modele.*;

import java.util.List;

/**
 * Classe représentant la vue de la table de craft.
 * Elle affiche les recettes disponibles et permet de crafter des objets.
 */
public class TableCraftVue {

    private final Pane paneCraft;
    private final TableCraft tableCraft;
    private final Inventaire inventaire;
    private final InventaireVue inventaireVue;

    /**
     * Constructeur de la classe TableCraftVue.
     *
     * @param paneCraft Le conteneur où les éléments de craft seront affichés.
     * @param tableCraft La table de craft contenant les recettes.
     * @param inventaire L'inventaire du joueur pour vérifier les composants nécessaires.
     * @param inventaireVue La vue de l'inventaire pour mettre à jour l'affichage après un craft.
     */
    public TableCraftVue(Pane paneCraft, TableCraft tableCraft, Inventaire inventaire, InventaireVue inventaireVue) {
        this.paneCraft = paneCraft;
        this.tableCraft = tableCraft;
        this.inventaire = inventaire;
        this.inventaireVue = inventaireVue;
    }

    /**
     * Affiche les recettes de craft dans le paneCraft.
     * Pour chaque recette, affiche les composants, le résultat et un bouton pour crafter.
     */
    public void afficher() {
        paneCraft.getChildren().clear();

        List<Recette> recettes = tableCraft.getRecettes();
        int y = 10;

        for (Recette recette : recettes) {
            int x = 20;
            // Affichage des composants
            for (Objet composant : recette.getComposants()) {
                try {
                    Image image = new Image(getClass().getResource("/images/" + composant.getNom() + ".png").toExternalForm());
                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(32);
                    imageView.setFitHeight(32);
                    imageView.setLayoutX(x);
                    imageView.setLayoutY(y);
                    paneCraft.getChildren().add(imageView);
                    x += 40;
                } catch (Exception e) {
                    System.out.println("Image composant non trouvée : " + composant.getNom());
                }
            }

            // Flèche "→"
            Text fleche = new Text("→");
            fleche.setLayoutX(x);
            fleche.setLayoutY(y + 20);
            paneCraft.getChildren().add(fleche);
            x += 30;

            // Image du résultat
            Objet resultat = recette.getResultat();
            try {
                Image image = new Image(getClass().getResource("/images/" + resultat.getNom() + ".png").toExternalForm());
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(32);
                imageView.setFitHeight(32);
                imageView.setLayoutX(x);
                imageView.setLayoutY(y);
                paneCraft.getChildren().add(imageView);
            } catch (Exception e) {
                System.out.println("Image résultat non trouvée : " + resultat.getNom());
            }

            // Bouton Craft
            Button bouton = new Button("Craft");
            bouton.setLayoutX(300);
            bouton.setLayoutY(y);
            boolean peutCrafter = recette.estCraftable(inventaire);

            if (!peutCrafter) {
                bouton.setDisable(true);
                bouton.setStyle("-fx-opacity: 0.6; -fx-text-fill: grey;");
                bouton.setTooltip(new Tooltip("Ingrédients manquants"));
            }

            bouton.setOnAction(e -> {
                if (recette.estCraftable(inventaire)) {
                    tableCraft.crafter(recette, inventaire);
                    inventaireVue.maj();
                    afficher(); // rafraîchir
                }
            });

            paneCraft.getChildren().add(bouton);

            y += 50;
        }
    }
}
