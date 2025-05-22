package universite_paris8.iut.youadah.projet.vue;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import universite_paris8.iut.youadah.projet.modele.Player;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;


public class PlayerVue {
    private final Image spriteDroite = new Image(getClass().getResource("/images/PersoRight.png").toExternalForm());
    private final Image spriteGauche = new Image(getClass().getResource("/images/PersoLeft.png").toExternalForm());
    private final ImageView imageJoueur;

    private final Player joueur;
    private boolean isBlesse = false;


    private boolean versLaDroite = true;

    private final Image spriteDegatDroite = new Image(getClass().getResource("/images/PersoRight-Degat.png").toExternalForm());
    private final Image spriteDegatGauche = new Image(getClass().getResource("/images/PersoLeft-Degat.png").toExternalForm());


    public void afficherMort() {
        // Met l’image du joueur en position "dégâts"
        imageJoueur.setImage(joueur.estsVersLaDroite() ? spriteDegatDroite : spriteDegatGauche);

        imageJoueur.setRotate(90);

        // Optionnel : désactive le binding vertical (pour que le corps reste bien posé au sol)
        imageJoueur.translateYProperty().unbind();
        imageJoueur.setTranslateY(joueur.getY() - 16); // ajuste si besoin selon ton visuel
    }


    public void setEtatBlesse(boolean blesse) {
        isBlesse = blesse;
        if (blesse) {
            imageJoueur.setImage(joueur.estsVersLaDroite() ? spriteDegatDroite : spriteDegatGauche);
        } else {
            imageJoueur.setImage(joueur.estsVersLaDroite() ? spriteDroite : spriteGauche);
        }
    }





    // ici dans le constructeur on charge l'image actuelle du joueur on redimentionne l'image ,et positionne le sprite a l'écrans
        public PlayerVue(Player joueur) {
            this.joueur = joueur;
            imageJoueur = new ImageView(spriteDroite);
            imageJoueur.setFitWidth(64);
            imageJoueur.setFitHeight(64);
            imageJoueur.translateXProperty().bind(joueur.xProperty().subtract(16).asObject());
            imageJoueur.translateYProperty().bind(joueur.yProperty().subtract(32).asObject());
        }



    // mis a jour de la position du joueur dans la map au niveau visuel cette methode est relance a chaque fois dans le Gamecontroller
    public void mettreAJourJoueur(Player joueur) {
        versLaDroite = joueur.estsVersLaDroite();
        if (!isBlesse) { // tu ajoutes un champ isBlesse aussi
            imageJoueur.setImage(versLaDroite ? spriteDroite : spriteGauche);
        }
    }

    public void animerClignotementDegats() {
        Timeline clignotement = new Timeline(
                new KeyFrame(Duration.seconds(0.1), e -> imageJoueur.setVisible(false)),
                new KeyFrame(Duration.seconds(0.2), e -> imageJoueur.setVisible(true)),
                new KeyFrame(Duration.seconds(0.3), e -> imageJoueur.setVisible(false)),
                new KeyFrame(Duration.seconds(0.4), e -> imageJoueur.setVisible(true))
        );
        clignotement.setCycleCount(1);
        clignotement.play();
    }




    public ImageView getNode() {

        return imageJoueur;
    }
}