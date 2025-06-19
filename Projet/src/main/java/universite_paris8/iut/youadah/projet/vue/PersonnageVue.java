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
 * Classe représentant la vue d'un personnage dans le jeu.
 * Elle gère l'affichage du sprite du personnage et sa position.
 */
public class PersonnageVue {
    protected Image spriteDroite ;
    protected Image spriteGauche;
    protected final ImageView imageJoueur;

    private final Personnage personnage;
    protected boolean isBlesse = false;

    protected boolean versLaDroite = true;

    /**
     * Constructeur de la classe PersonnageVue.
     * @param personnage Le personnage à représenter dans la vue.
     */
    public PersonnageVue(Personnage personnage) {
        this.personnage = personnage;
        imageJoueur = new ImageView();
        imageJoueur.setFitWidth(64);
        imageJoueur.setFitHeight(64);
        imageJoueur.translateXProperty().bind(personnage.xProperty().subtract(16).asObject());
        imageJoueur.translateYProperty().bind(personnage.yProperty().subtract(32).asObject());
    }


    /**
     * Met à jour l'affichage du personnage en fonction de son état.
     * Change l'image affichée en fonction de la direction du personnage.
     * @param personnage Le personnage dont on met à jour la vue.
     */
    public void mettreAJour(Personnage personnage) {
        versLaDroite = personnage.estsVersLaDroite();
        if (!isBlesse) { // tu ajoutes un champ isBlesse aussi
            imageJoueur.setImage(versLaDroite ? spriteDroite : spriteGauche);
        }
    }

    /**
     * Retourne le nœud ImageView du personnage pour l'ajouter à la scène.
     * @return L'ImageView représentant le personnage.
     */
    public ImageView getNode() {
        return imageJoueur;
    }
}