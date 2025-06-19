package universite_paris8.iut.youadah.projet.vue;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import universite_paris8.iut.youadah.projet.modele.Personnage;
import universite_paris8.iut.youadah.projet.modele.Player;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 * Classe qui gère l'affichage graphique d'un personnage dans le jeu.
 * Elle fait le lien entre le modèle (Personnage) et sa représentation visuelle.
 */
public class PersonnageVue {
    /** Image du personnage orienté vers la droite */
    protected Image spriteDroite ;
    /** Image du personnage orienté vers la gauche */
    protected Image spriteGauche;
    /** Composant graphique qui affiche l'image du personnage */
    protected final ImageView imageJoueur;

    /** Référence au modèle du personnage */
    private final Personnage personnage;
    /** Indique si le personnage est actuellement blessé */
    protected boolean isBlesse = false;

    /** Indique si le personnage est orienté vers la droite */
    protected boolean versLaDroite = true;

    /**
     * Constructeur de la vue du personnage
     * @param personnage Le modèle du personnage à afficher
     */
    public PersonnageVue(Personnage personnage) {
        this.personnage = personnage;
        imageJoueur = new ImageView(spriteDroite);
        imageJoueur.setFitWidth(64);
        imageJoueur.setFitHeight(64);
        // Lie la position de l'image à la position du personnage avec un décalage
        imageJoueur.translateXProperty().bind(personnage.xProperty().subtract(16).asObject());
        imageJoueur.translateYProperty().bind(personnage.yProperty().subtract(32).asObject());
    }

    /**
     * Met à jour l'affichage du personnage en fonction de son état
     * @param personnage Le modèle du personnage à mettre à jour
     */
    public void mettreAJour(Personnage personnage) {
        versLaDroite = personnage.estsVersLaDroite();
        // Ne change pas l'image si le personnage est blessé
        if (!isBlesse) {
            imageJoueur.setImage(versLaDroite ? spriteDroite : spriteGauche);
        }
    }

    /**
     * Récupère le composant graphique qui représente le personnage
     * @return Le nœud JavaFX qui affiche le personnage
     */
    public ImageView getNode() {
        return imageJoueur;
    }
}
