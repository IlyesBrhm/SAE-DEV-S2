package universite_paris8.iut.youadah.projet.vue;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import universite_paris8.iut.youadah.projet.modele.Player;

public class PlayerVue {
    private final Image spriteDroite = new Image(getClass().getResource("/images/PersoRight.png").toExternalForm());
    private final Image spriteGauche = new Image(getClass().getResource("/images/PersoLeft.png").toExternalForm());
    private final ImageView imageJoueur;

    private Pane pane;

    // ici dans le constructeur on charge l'image actuelle du joueur on redimentionne l'image ,et positionne le sprite a l'écrans
    public PlayerVue(Player joueur, Pane p) {
        imageJoueur = new ImageView(spriteDroite);
        imageJoueur.setFitWidth(64);
        imageJoueur.setFitHeight(64);
        imageJoueur.translateXProperty().bind(joueur.xProperty().subtract(16).asObject());
        imageJoueur.translateYProperty().bind(joueur.yProperty().subtract(32).asObject());

        pane = p;
    }
    // mis a jour de la position du joueur dans la map au niveau visuel cette methode est relance a chaque fois dans le Gamecontroller
    public void mettreAJourJoueur(Player joueur) {
        imageJoueur.setImage(joueur.estsVersLaDroite() ? spriteDroite : spriteGauche);
    }

    public void afficherJoueur() {
        pane.getChildren().add(imageJoueur);
    }

    public ImageView getNode() {

        return imageJoueur;
    }
}