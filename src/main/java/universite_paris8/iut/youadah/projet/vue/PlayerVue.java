package universite_paris8.iut.youadah.projet.vue;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import universite_paris8.iut.youadah.projet.modele.Player;
import universite_paris8.iut.youadah.projet.modele.Epee;
import universite_paris8.iut.youadah.projet.modele.Arc;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class PlayerVue {
    private Image spriteDroite;
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
    private final Image spriteEpeeDroite = new Image(getClass().getResource("/images/PersoRightEpee.png").toExternalForm());
    private final Image spriteEpeeGauche = new Image(getClass().getResource("/images/PersoLeftEpee.png").toExternalForm());
    private final Image spriteArcDroite = new Image(getClass().getResource("/images/PersoRightArc.png").toExternalForm());
    private final Image spriteArcGauche = new Image(getClass().getResource("/images/PersoLeftArc.png").toExternalForm());

    public PlayerVue(Player joueur, Pane p) {
        this.joueur = joueur;
        spriteDroite = new Image(getClass().getResource("/images/PersoRight.png").toExternalForm());
        spriteGauche = new Image(getClass().getResource("/images/PersoLeft.png").toExternalForm());

        imageJoueur = new ImageView(spriteDroite);
        imageJoueur.setFitWidth(64);
        imageJoueur.setFitHeight(64);
        imageJoueur.translateXProperty().bind(joueur.xProperty().subtract(16).asObject());
        imageJoueur.translateYProperty().bind(joueur.yProperty().subtract(32).asObject());
        this.pane = p;
    }

    public void mettreAJourJoueur(Player joueur) {
        versLaDroite = joueur.estsVersLaDroite();

        if (!isBlesse) {
            imageJoueur.setImage(versLaDroite ? spriteDroite : spriteGauche);
        }

        if (joueur.getObjetPossede() != null) {
            String nomObjet = joueur.getObjetPossede().getNom().toLowerCase();

            if (nomObjet.equals("pioche")) {
                imageJoueur.setImage(versLaDroite ? spritePiocheDroite : spritePiocheGauche);
            } else if (nomObjet.contains("potion")) {
                imageJoueur.setImage(versLaDroite ? spritePotionDroite : spritePotionGauche);
            } else if (joueur.getObjetPossede() instanceof Epee) {
                imageJoueur.setImage(versLaDroite ? spriteEpeeDroite : spriteEpeeGauche);
            } else if (joueur.getObjetPossede() instanceof Arc) {
                imageJoueur.setImage(versLaDroite ? spriteArcDroite : spriteArcGauche);
            }
        }
    }

    public void remettrePersoDeBase(Player joueur) {
        versLaDroite = joueur.estsVersLaDroite();
        imageJoueur.setImage(versLaDroite ? spriteDroite : spriteGauche);
    }

    public ImageView getNode() {
        return imageJoueur;
    }
}
