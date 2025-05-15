package universite_paris8.iut.youadah.projet.vue;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import universite_paris8.iut.youadah.projet.modele.Player;

/**
 * Classe chargée de représenter visuellement le joueur à l'écran.
 */
public class PlayerVue {

    private final ImageView imageJoueur;



    public PlayerVue(Player joueur) {
        imageJoueur = new ImageView(joueur.getSpriteActuel());
        imageJoueur.setFitWidth(64);
        imageJoueur.setFitHeight(64);
        imageJoueur.setTranslateX(joueur.getX());
        imageJoueur.setTranslateY(joueur.getY() - 32); // Ajustement pour l'ancrage au sol


    }

    public void mettreAJour(Player joueur) {
        imageJoueur.setImage(joueur.getSpriteActuel());
        imageJoueur.setTranslateX(joueur.getX());
        imageJoueur.setTranslateY(joueur.getY() - 32);
    }

    public ImageView getNode() {
        return imageJoueur;
    }
}
