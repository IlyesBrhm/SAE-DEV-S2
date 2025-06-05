package universite_paris8.iut.youadah.projet.vue;

import universite_paris8.iut.youadah.projet.modele.Objet;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.awt.*;

public class ObjetVue {
    private Objet objet;
    private Image image;
    private ImageView imageView;

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
