package universite_paris8.iut.youadah.projet.vue;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import universite_paris8.iut.youadah.projet.modele.entite.Personnage;


public class PersonnageVue {
    protected Image spriteDroite ;
    protected Image spriteGauche;
    protected final ImageView imageJoueur;

    private final Personnage personnage;
    protected boolean isBlesse = false;

    protected boolean versLaDroite = true;

    public PersonnageVue(Personnage personnage) {
        this.personnage = personnage;
        imageJoueur = new ImageView(spriteDroite);
        imageJoueur.setFitWidth(64);
        imageJoueur.setFitHeight(64);
        imageJoueur.translateXProperty().bind(personnage.xProperty().subtract(16).asObject());
        imageJoueur.translateYProperty().bind(personnage.yProperty().subtract(32).asObject());
    }

    public void mettreAJour(Personnage personnage) {
        versLaDroite = personnage.estsVersLaDroite();
        if (!isBlesse) { // tu ajoutes un champ isBlesse aussi
            imageJoueur.setImage(versLaDroite ? spriteDroite : spriteGauche);
        }
    }

    public ImageView getNode() {
        return imageJoueur;
    }
}