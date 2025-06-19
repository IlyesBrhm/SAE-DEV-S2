package universite_paris8.iut.youadah.projet.vue;

import universite_paris8.iut.youadah.projet.modele.Objet;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.awt.*;

/**
 * Classe représentant la vue d'un objet dans le jeu.
 * Elle contient l'objet et son image associée.
 */
public class ObjetVue {
    private Objet objet;
    private Image image;
    private ImageView imageView;

    /**
     * Constructeur de la classe ObjetVue.
     * @param o L'objet à représenter dans la vue.
     */
    public ObjetVue (Objet o){
        objet = o;
        image = new javafx.scene.image.Image(getClass().getResource("/images/" + objet.getNom() + ".png").toExternalForm());
        imageView = new ImageView(image);
    }

    public Objet getObjet() {
        return objet;
    }

    public ImageView getImageView() {
        return imageView;
    }
}
