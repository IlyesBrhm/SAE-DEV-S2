package universite_paris8.iut.youadah.projet.vue;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/**
 * Classe qui gère l'affichage des coeurs représentant les points de vie du joueur.
 * Elle affiche une image de coeur qui change en fonction du niveau de vie actuel.
 */
public class CoeurVue {
    // Conteneur horizontal pour afficher les coeurs
    private final HBox barreVie;
    // Images des coeurs pour différents niveaux de vie (5 à 0)
    private final Image coeur5;
    private final Image coeur4;
    private final Image coeur3;
    private final Image coeur2;
    private final Image coeur1;
    private final Image coeur0;

    // Vue pour afficher l'image du coeur
    private final ImageView coeurView;
    // Points de vie actuels
    private int pv;
    // Panneau parent (non utilisé dans cette classe)
    private Pane pane;

    /**
     * Constructeur qui initialise l'affichage du coeur avec un niveau de vie initial.
     * 
     * @param pv Points de vie initiaux
     */
    public CoeurVue(int pv) {
        this.pv = pv;
        // Création du conteneur avec un espacement de 5 pixels
        this.barreVie = new HBox(5);
        // Chargement des images pour chaque niveau de vie
        this.coeur5 = new Image(getClass().getResource("/images/heart5.png").toExternalForm());
        this.coeur4 = new Image(getClass().getResource("/images/heart4.png").toExternalForm());
        this.coeur3 = new Image(getClass().getResource("/images/heart3.png").toExternalForm());
        this.coeur2 = new Image(getClass().getResource("/images/heart2.png").toExternalForm());
        this.coeur1 = new Image(getClass().getResource("/images/heart1.png").toExternalForm());
        this.coeur0 = new Image(getClass().getResource("/images/heart0.png").toExternalForm());

        // Création de la vue pour afficher l'image du coeur
        this.coeurView = new ImageView();
        // Définition de la taille de l'image (32x32 pixels)
        coeurView.setFitWidth(32);
        coeurView.setFitHeight(32);
        // Ajout de la vue au conteneur
        barreVie.getChildren().add(coeurView);

        // Initialisation de l'image du coeur en fonction des points de vie
        mettreAJourPv(pv); // Initialisation
    }

    /**
     * Met à jour l'affichage du coeur en fonction du niveau de vie actuel.
     * Change l'image du coeur selon le nombre de points de vie.
     * Si les points de vie sont à 0 ou négatifs, le coeur disparaît.
     * 
     * @param pv Nouveau nombre de points de vie
     */
    public void mettreAJourPv(int pv) {
        this.pv = pv;
        // Sélection de l'image appropriée selon le niveau de vie
        switch (pv) {
            case 5 -> coeurView.setImage(coeur5);
            case 4 -> coeurView.setImage(coeur4);
            case 3 -> coeurView.setImage(coeur3);
            case 2 -> coeurView.setImage(coeur2);
            case 1 -> coeurView.setImage(coeur1);
            case 0 -> coeurView.setImage(coeur0);
            default -> barreVie.getChildren().clear(); // Supprime tous les éléments si pv invalide
        }

        // S'assure que le coeur est visible si les points de vie sont positifs
        if (pv > 0 && !barreVie.getChildren().contains(coeurView)) {
            barreVie.getChildren().add(coeurView);
        }
    }

    /**
     * Retourne le conteneur avec l'affichage du coeur.
     * 
     * @return Le conteneur HBox contenant l'affichage du coeur
     */
    public HBox getBarreVie() {
        return barreVie;
    }
}
