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

    private final Image spriteDegatDroite = new Image(getClass().getResource("/images/PersoRight-Degat.png").toExternalForm());
    private final Image spriteDegatGauche = new Image(getClass().getResource("/images/PersoLeft-Degat.png").toExternalForm());

    private final Image spritePiocheDroite = new Image(getClass().getResource("/images/persoRightPioche.png").toExternalForm());
    private final Image spritePiocheGauche = new Image(getClass().getResource("/images/PersoLeftPioche.png").toExternalForm());

    private final Image spritePotionDroite = new Image(getClass().getResource("/images/PersoRightPotion.png").toExternalForm());
    private final Image spritePotionGauche = new Image(getClass().getResource("/images/PersoLeftPotion.png").toExternalForm());


    public void afficherMort() {
        imageJoueur.setImage(joueur.estsVersLaDroite() ? spriteDegatDroite : spriteDegatGauche);

        imageJoueur.setRotate(90);

        imageJoueur.translateYProperty().unbind();
        imageJoueur.setTranslateY(joueur.getY() - 16);
    }


    public void setEtatBlesse(boolean blesse) {
        isBlesse = blesse;
        if (blesse) {
            imageJoueur.setImage(joueur.estsVersLaDroite() ? spriteDegatDroite : spriteDegatGauche);
        } else {
            imageJoueur.setImage(joueur.estsVersLaDroite() ? spriteDroite : spriteGauche);
        }
    }





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

    public Image getSpriteDroite() {
        return spriteDroite;
    }

    public Image getSpriteGauche() {
        return spriteGauche;
    }

    public void setSpriteDroite(Image spriteDroite) {
        this.spriteDroite = spriteDroite;
    }

    public void setSpriteGauche(Image spriteGauche) {
        this.spriteGauche = spriteGauche;
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



    public void afficherJoueur() {
        pane.getChildren().add(imageJoueur);
    }

    public ImageView getNode() {

        return imageJoueur;
    }
}