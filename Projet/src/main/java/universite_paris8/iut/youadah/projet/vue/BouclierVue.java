package universite_paris8.iut.youadah.projet.vue;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/** * Classe représentant la vue du bouclier du joueur.
 * Affiche une barre de bouclier avec des images représentant les points de vie restants.
 */
public class BouclierVue {
    private final HBox barreBouclier;
    private final Image bouclier5;
    private final Image bouclier4;
    private final Image bouclier3;
    private final Image bouclier2;
    private final Image bouclier1;
    private final Image bouclier0;

    private final ImageView bouclierView;
    private int pv;
    private Pane pane;

    /**
     * Constructeur de la vue du bouclier.
     * @param pv Points de vie initiaux du bouclier.
     * @param p Pane dans lequel le bouclier sera affiché.
     */
    public BouclierVue(int pv, Pane p) {
        this.pv = pv;
        this.barreBouclier = new HBox(5);
        this.bouclier5 = new Image(getClass().getResource("/images/bouclier5.png").toExternalForm());
        this.bouclier4 = new Image(getClass().getResource("/images/bouclier4.png").toExternalForm());
        this.bouclier3 = new Image(getClass().getResource("/images/bouclier3.png").toExternalForm());
        this.bouclier2 = new Image(getClass().getResource("/images/bouclier2.png").toExternalForm());
        this.bouclier1 = new Image(getClass().getResource("/images/bouclier1.png").toExternalForm());
        this.bouclier0 = new Image(getClass().getResource("/images/bouclier0.png").toExternalForm());

        this.bouclierView = new ImageView();
        bouclierView.setFitWidth(32);
        bouclierView.setFitHeight(32);
        barreBouclier.getChildren().add(bouclierView);

        pane = p;

        mettreAJourPv(pv); // Initialisation
    }

    /**
     * Met à jour les points de vie du bouclier et change l'image affichée en conséquence.
     * @param pv Points de vie restants du bouclier.
     */
    public void mettreAJourPv(int pv) {
        this.pv = pv;
        switch (pv) {
            case 5 -> bouclierView.setImage(bouclier5);
            case 4 -> bouclierView.setImage(bouclier4);
            case 3 -> bouclierView.setImage(bouclier3);
            case 2 -> bouclierView.setImage(bouclier2);
            case 1 -> bouclierView.setImage(bouclier1);
            case 0 -> bouclierView.setImage(bouclier0);
            default -> barreBouclier.getChildren().clear();
        }

        if (pv > 0 && !barreBouclier.getChildren().contains(bouclierView)) {
            barreBouclier.getChildren().add(bouclierView);
        }
    }

    public HBox getBarreBouclier() {
        return barreBouclier;
    }

}