package universite_paris8.iut.youadah.projet.vue;

import javafx.scene.image.Image;
import universite_paris8.iut.youadah.projet.modele.Ennemie;
import universite_paris8.iut.youadah.projet.modele.Personnage;

public class EnnemieVue extends PersonnageVue {

    public EnnemieVue(Ennemie ennemie) {
        super(ennemie);
        spriteDroite = new Image(getClass().getResource("/images/MonstreD.png").toExternalForm());
        spriteGauche = new Image(getClass().getResource("/images/MonstreG.png").toExternalForm());
        imageJoueur.setImage(spriteDroite); // ← indispensable
    }



}