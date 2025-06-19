package universite_paris8.iut.youadah.projet.vue;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/**
 * Classe représentant la vue des cœurs du joueur.
 * Affiche une barre de vie avec des images représentant les points de vie restants.
 */
public class CoeurVue {
    private final HBox barreVie;
    private final Image coeur5;
    private final Image coeur4;
    private final Image coeur3;
    private final Image coeur2;
    private final Image coeur1;
    private final Image coeur0;

    private final ImageView coeurView;
    private int pv;

    /**
     * Constructeur de la vue des cœurs.
     * @param pv Points de vie initiaux du joueur.
     */
    public CoeurVue(int pv) {
        this.pv = pv;
        this.barreVie = new HBox(5);
        this.coeur5 = new Image(getClass().getResource("/images/heart5.png").toExternalForm());
        this.coeur4 = new Image(getClass().getResource("/images/heart4.png").toExternalForm());
        this.coeur3 = new Image(getClass().getResource("/images/heart3.png").toExternalForm());
        this.coeur2 = new Image(getClass().getResource("/images/heart2.png").toExternalForm());
        this.coeur1 = new Image(getClass().getResource("/images/heart1.png").toExternalForm());
        this.coeur0 = new Image(getClass().getResource("/images/heart0.png").toExternalForm());

        this.coeurView = new ImageView();
        coeurView.setFitWidth(32);
        coeurView.setFitHeight(32);
        barreVie.getChildren().add(coeurView);

        mettreAJourPv(pv); // Initialisation
    }

    /**
     * Met à jour les points de vie du joueur et change l'image affichée en conséquence.
     * @param pv Points de vie restants du joueur.
     */
    public void mettreAJourPv(int pv) {
        this.pv = pv;
        switch (pv) {
            case 5 -> coeurView.setImage(coeur5);
            case 4 -> coeurView.setImage(coeur4);
            case 3 -> coeurView.setImage(coeur3);
            case 2 -> coeurView.setImage(coeur2);
            case 1 -> coeurView.setImage(coeur1);
            case 0 -> coeurView.setImage(coeur0);
            default -> barreVie.getChildren().clear();
        }

        if (pv > 0 && !barreVie.getChildren().contains(coeurView)) {
            barreVie.getChildren().add(coeurView);
        }
    }

    public HBox getBarreVie() {
        return barreVie;
    }
}