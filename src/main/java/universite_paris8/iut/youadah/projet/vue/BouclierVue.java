package universite_paris8.iut.youadah.projet.vue;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/**
 * Classe responsable de l'affichage du bouclier du joueur dans l'interface graphique.
 * Gère les différentes images de bouclier selon le niveau de protection (0 à 5).
 */
public class BouclierVue {
    // Conteneur horizontal pour l'affichage du bouclier
    private final HBox barreBouclier;
    // Images des différents états du bouclier (5 = plein, 0 = vide)
    private final Image bouclier5;
    private final Image bouclier4;
    private final Image bouclier3;
    private final Image bouclier2;
    private final Image bouclier1;
    private final Image bouclier0;

    // Composant d'affichage de l'image du bouclier
    private final ImageView bouclierView;
    // Points de vie/protection actuels du bouclier
    private int pv;
    // Panneau parent où le bouclier sera affiché
    private Pane pane;

    /**
     * Constructeur de la classe BouclierVue.
     * 
     * @param pv Niveau initial de protection du bouclier (0-5)
     * @param p Panneau parent où le bouclier sera affiché
     */
    public BouclierVue(int pv, Pane p) {
        this.pv = pv;
        // Création du conteneur avec un espacement de 5 pixels
        this.barreBouclier = new HBox(5);
        // Chargement des images pour chaque niveau de bouclier
        this.bouclier5 = new Image(getClass().getResource("/images/bouclier5.png").toExternalForm());
        this.bouclier4 = new Image(getClass().getResource("/images/bouclier4.png").toExternalForm());
        this.bouclier3 = new Image(getClass().getResource("/images/bouclier3.png").toExternalForm());
        this.bouclier2 = new Image(getClass().getResource("/images/bouclier2.png").toExternalForm());
        this.bouclier1 = new Image(getClass().getResource("/images/bouclier1.png").toExternalForm());
        this.bouclier0 = new Image(getClass().getResource("/images/bouclier0.png").toExternalForm());

        // Création du composant d'affichage de l'image
        this.bouclierView = new ImageView();
        // Configuration de la taille du bouclier (32x32 pixels)
        bouclierView.setFitWidth(32);
        bouclierView.setFitHeight(32);
        // Ajout de l'image au conteneur
        barreBouclier.getChildren().add(bouclierView);

        // Stockage du panneau parent
        pane = p;

        // Initialisation de l'affichage avec le niveau de protection fourni
        mettreAJourPv(pv); // Initialisation
    }

    /**
     * Met à jour l'affichage du bouclier en fonction du niveau de protection.
     * Change l'image affichée selon le nombre de points de vie restants.
     * 
     * @param pv Niveau de protection actuel (0-5)
     */
    public void mettreAJourPv(int pv) {
        this.pv = pv;
        // Sélection de l'image appropriée selon le niveau de protection
        switch (pv) {
            case 5 -> bouclierView.setImage(bouclier5);  // Bouclier plein
            case 4 -> bouclierView.setImage(bouclier4);  // Bouclier à 4/5
            case 3 -> bouclierView.setImage(bouclier3);  // Bouclier à 3/5
            case 2 -> bouclierView.setImage(bouclier2);  // Bouclier à 2/5
            case 1 -> bouclierView.setImage(bouclier1);  // Bouclier à 1/5
            case 0 -> bouclierView.setImage(bouclier0);  // Bouclier vide
            default -> barreBouclier.getChildren().clear();  // Cas invalide, on vide le conteneur
        }

        // Si le bouclier a des points de vie et n'est pas déjà affiché, on l'ajoute au conteneur
        if (pv > 0 && !barreBouclier.getChildren().contains(bouclierView)) {
            barreBouclier.getChildren().add(bouclierView);
        }
    }

    /**
     * Récupère le conteneur du bouclier pour l'affichage dans l'interface.
     * 
     * @return Le conteneur HBox contenant l'image du bouclier
     */
    public HBox getBarreBouclier() {
        return barreBouclier;
    }

    /**
     * Ajoute l'image du bouclier directement au panneau parent.
     * Utilisé pour afficher le bouclier dans l'interface.
     */
    public void afficherBouclier() {
        pane.getChildren().add(bouclierView);
    }
}
