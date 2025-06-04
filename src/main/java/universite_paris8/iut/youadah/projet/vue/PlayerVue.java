package universite_paris8.iut.youadah.projet.vue;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import universite_paris8.iut.youadah.projet.modele.Player;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;


public class PlayerVue {
    private Image spriteDroite ;
    private Image spriteGauche;
    private final ImageView imageJoueur;
    private Pane pane;

    private final Player joueur;
    private boolean isBlesse = false;


    private boolean versLaDroite = true;

    private final Image spritePiocheDroite = new Image(getClass().getResource("/images/persoRightPioche.png").toExternalForm());
    private final Image spritePiocheGauche = new Image(getClass().getResource("/images/PersoLeftPioche.png").toExternalForm());
    private final Image spritePotionDroite = new Image(getClass().getResource("/images/PersoRightPotion.png").toExternalForm());
    private final Image spritePotionGauche = new Image(getClass().getResource("/images/PersoLeftPotion.png").toExternalForm());


    public PlayerVue(Player joueur, Pane p) {
        this.joueur = joueur;
        imageJoueur = new ImageView(spriteDroite);
        imageJoueur.setFitWidth(64);
        imageJoueur.setFitHeight(64);
        imageJoueur.translateXProperty().bind(joueur.xProperty().subtract(16).asObject());
        imageJoueur.translateYProperty().bind(joueur.yProperty().subtract(32).asObject());
        spriteDroite = new Image(getClass().getResource("/images/PersoRight.png").toExternalForm());
        spriteGauche = new Image(getClass().getResource("/images/PersoLeft.png").toExternalForm());
        pane = p;
    }

    public void mettreAJourJoueur(Player joueur) {
        versLaDroite = joueur.estsVersLaDroite();
        if (!isBlesse) { // tu ajoutes un champ isBlesse aussi
            imageJoueur.setImage(versLaDroite ? spriteDroite : spriteGauche);
        }
        if (joueur.getObjetPossede() != null){
            if (joueur.getObjetPossede().getNom() == "pioche"){
                imageJoueur.setImage(versLaDroite ? spritePiocheDroite : spritePiocheGauche);
            }
            if (joueur.getObjetPossede().getNom().contains("potion")){
                imageJoueur.setImage(versLaDroite ? spritePotionDroite : spritePotionGauche);
            }
        }
    }

    public void remettrePersoDeBase(Player joueur){
        versLaDroite = joueur.estsVersLaDroite();
        imageJoueur.setImage(versLaDroite ? spriteDroite : spriteGauche);
    }

    public ImageView getNode() {
        return imageJoueur;
    }
}