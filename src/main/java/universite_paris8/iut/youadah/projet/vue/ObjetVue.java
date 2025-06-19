package universite_paris8.iut.youadah.projet.vue;

import universite_paris8.iut.youadah.projet.modele.Objet;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.awt.*;

/**
 * Classe représentant la vue d'un objet dans le jeu
 * Cette classe permet d'afficher un objet avec son image correspondante
 */
public class ObjetVue {
    // L'objet du modèle associé à cette vue
    private Objet objet;
    // L'image de l'objet
    private Image image;
    // Le composant JavaFX qui affiche l'image
    private ImageView imageView;

    /**
     * Constructeur de la vue d'un objet
     * @param o L'objet du modèle à afficher
     */
    public ObjetVue (Objet o){
        // Stocke l'objet du modèle
        objet = o;
        // Charge l'image correspondant au nom de l'objet depuis les ressources
        image = new javafx.scene.image.Image(getClass().getResource("/images/" + objet.getNom() + ".png").toExternalForm());
        // Crée un composant ImageView pour afficher l'image
        imageView = new ImageView(image);
    }

    /**
     * Récupère l'objet du modèle associé à cette vue
     * @return L'objet du modèle
     */
    public Objet getObjet() {
        return objet;
    }

    /**
     * Récupère le composant ImageView pour l'affichage
     * @return Le composant ImageView contenant l'image de l'objet
     */
    public ImageView getImageView() {
        return imageView;
    }
}
