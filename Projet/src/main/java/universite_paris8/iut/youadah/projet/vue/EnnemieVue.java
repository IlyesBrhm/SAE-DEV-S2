package universite_paris8.iut.youadah.projet.vue;

import javafx.scene.image.Image;
import universite_paris8.iut.youadah.projet.modele.entite.Ennemie;

public class EnnemieVue extends PersonnageVue {

    public EnnemieVue(Ennemie ennemie) {
        super(ennemie);
        spriteDroite = new Image(getClass().getResource("/images/PersoRight.png").toExternalForm());
        spriteGauche = new Image(getClass().getResource("/images/PersoLeft.png").toExternalForm());
    }

}