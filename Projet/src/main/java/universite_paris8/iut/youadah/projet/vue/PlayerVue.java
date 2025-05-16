package universite_paris8.iut.youadah.projet.vue;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import universite_paris8.iut.youadah.projet.modele.Player;
import universite_paris8.iut.youadah.projet.controller.GameController;

public class PlayerVue {

    private final ImageView imageJoueur;



    public PlayerVue(Player joueur) {
        imageJoueur = new ImageView(joueur.getSpriteActuel());
        imageJoueur.setFitWidth(64);
        imageJoueur.setFitHeight(64);
        imageJoueur.setTranslateX(Math.round(joueur.getX())-16);
        imageJoueur.setTranslateY(Math.round(joueur.getY() - 32));


    }

    public void mettreAJour(Player joueur) {
        imageJoueur.setImage(joueur.getSpriteActuel());
        imageJoueur.setTranslateX(Math.round(joueur.getX())-16);
        imageJoueur.setTranslateY(Math.round(joueur.getY() - 32));


    }

    public ImageView getNode() {
        return imageJoueur;
    }
}