package universite_paris8.iut.youadah.projet.vue;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import universite_paris8.iut.youadah.projet.modele.Player;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;


public class PlayerVue extends PersonnageVue {

    private final Image spritePiocheDroite = new Image(getClass().getResource("/images/persoRightPioche.png").toExternalForm());
    private final Image spritePiocheGauche = new Image(getClass().getResource("/images/PersoLeftPioche.png").toExternalForm());

    private final Image spritePotionDroite = new Image(getClass().getResource("/images/PersoRightPotion.png").toExternalForm());
    private final Image spritePotionGauche = new Image(getClass().getResource("/images/PersoLeftPotion.png").toExternalForm());

    public PlayerVue(Player joueur) {
        super(joueur);
        spriteDroite = new Image(getClass().getResource("/images/PersoRight.png").toExternalForm());
        spriteGauche = new Image(getClass().getResource("/images/PersoLeft.png").toExternalForm());
    }

    public void mettreAJourJoueur(Player joueur) {
        mettreAJour(joueur);
        if (joueur.getObjetPossede() != null){
            if (joueur.getObjetPossede().getNom() == "pioche"){
                imageJoueur.setImage(versLaDroite ? spritePiocheDroite : spritePiocheGauche);
            }
            if (joueur.getObjetPossede().getNom().contains("potion")){
                imageJoueur.setImage(versLaDroite ? spritePotionDroite : spritePotionGauche);
            }
        }
    }
}