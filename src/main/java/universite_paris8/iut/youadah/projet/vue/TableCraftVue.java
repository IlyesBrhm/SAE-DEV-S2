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
 * Classe responsable de l'affichage de l'interface de crafting (fabrication d'objets)
 * Cette vue permet à l'utilisateur de visualiser les recettes disponibles et de fabriquer
 * des objets s'il possède les composants nécessaires dans son inventaire.
 */
public class TableCraftVue {

    // Panneau JavaFX contenant l'interface de crafting
    private final Pane paneCraft;
    // Modèle contenant la logique de crafting et les recettes
    private final TableCraft tableCraft;
    // Inventaire du joueur contenant ses objets
    private final Inventaire inventaire;
    // Vue de l'inventaire pour mettre à jour l'affichage après crafting
    private final InventaireVue inventaireVue;
    // Panneau contenant l'interface ATH (Affichage Tête Haute)
    private final Pane ath;

    /**
     * Constructeur de la vue de la table de craft
     * 
     * @param paneCraft Le panneau JavaFX où afficher l'interface de crafting
     * @param tableCraft Le modèle contenant les recettes et la logique de crafting
     * @param inventaire L'inventaire du joueur
     * @param inventaireVue La vue de l'inventaire à mettre à jour après crafting
     * @param ath Le panneau d'affichage tête haute
     */
    public TableCraftVue(Pane paneCraft, TableCraft tableCraft, Inventaire inventaire, InventaireVue inventaireVue, Pane ath) {
        this.paneCraft = paneCraft;
        this.tableCraft = tableCraft;
        this.inventaire = inventaire;
        this.inventaireVue = inventaireVue;
        this.ath = ath;
    }

    /**
     * Affiche l'interface de crafting avec toutes les recettes disponibles
     * Pour chaque recette, affiche:
     * - Les composants nécessaires
     * - Une flèche
     * - Le résultat du craft
     * - Un bouton pour crafter l'objet (désactivé si les ingrédients sont insuffisants)
     */
    public void afficher() {
        // Efface le contenu précédent du panneau
        paneCraft.getChildren().clear();

        // Récupère la liste des recettes disponibles
        List<Recette> recettes = tableCraft.getRecettes();
        // Position verticale initiale pour l'affichage
        int y = 10;

        // Parcours de toutes les recettes disponibles
        for (Recette recette : recettes) {
            // Position horizontale initiale pour l'affichage des composants
            int x = 20;

            // Affichage des composants (ingrédients) de la recette
            for (Objet composant : recette.getComposants()) {
                try {
                    // Chargement de l'image du composant depuis les ressources
                    Image image = new Image(getClass().getResource("/images/" + composant.getNom() + ".png").toExternalForm());
                    ImageView imageView = new ImageView(image);
                    // Configuration de la taille et position de l'image
                    imageView.setFitWidth(32);
                    imageView.setFitHeight(32);
                    imageView.setLayoutX(x);
                    imageView.setLayoutY(y);
                    // Ajout de l'image au panneau
                    paneCraft.getChildren().add(imageView);
                    // Décalage horizontal pour le prochain composant
                    x += 40;
                } catch (Exception e) {
                    // Gestion des erreurs si l'image n'est pas trouvée
                    System.out.println("Image composant non trouvée : " + composant.getNom());
                }
            }

            // Ajout d'une flèche "→" pour indiquer la transformation
            Text fleche = new Text("→");
            fleche.setLayoutX(x);
            fleche.setLayoutY(y + 20);
            paneCraft.getChildren().add(fleche);
            // Décalage horizontal après la flèche
            x += 30;

            // Affichage de l'image du résultat de la recette
            Objet resultat = recette.getResultat();
            try {
                // Chargement de l'image du résultat depuis les ressources
                Image image = new Image(getClass().getResource("/images/" + resultat.getNom() + ".png").toExternalForm());
                ImageView imageView = new ImageView(image);
                // Configuration de la taille et position de l'image
                imageView.setFitWidth(32);
                imageView.setFitHeight(32);
                imageView.setLayoutX(x);
                imageView.setLayoutY(y);
                // Ajout de l'image au panneau
                paneCraft.getChildren().add(imageView);
            } catch (Exception e) {
                // Gestion des erreurs si l'image n'est pas trouvée
                System.out.println("Image résultat non trouvée : " + resultat.getNom());
            }

            // Création du bouton pour crafter l'objet
            Button bouton = new Button("Craft");
            bouton.setLayoutX(300);
            bouton.setLayoutY(y);
            // Vérification si le joueur possède les ingrédients nécessaires
            boolean peutCrafter = recette.estCraftable(inventaire);

            // Si les ingrédients sont insuffisants, désactiver le bouton et ajouter une info-bulle
            if (!peutCrafter) {
                bouton.setDisable(true);
                bouton.setStyle("-fx-opacity: 0.6; -fx-text-fill: grey;");
                bouton.setTooltip(new Tooltip("Ingrédients manquants"));
            }

            // Configuration de l'action du bouton lors du clic
            bouton.setOnAction(e -> {
                // Vérification à nouveau si le craft est possible (au cas où l'inventaire a changé)
                if (recette.estCraftable(inventaire)) {
                    // Exécution du craft
                    tableCraft.crafter(recette, inventaire);
                    // Mise à jour de l'affichage de l'inventaire
                    inventaireVue.maj();
                    // Rafraîchissement de l'interface de crafting
                    afficher();
                }
            });

            // Ajout du bouton au panneau
            paneCraft.getChildren().add(bouton);

            // Décalage vertical pour la prochaine recette
            y += 50;
        }
    }
}
