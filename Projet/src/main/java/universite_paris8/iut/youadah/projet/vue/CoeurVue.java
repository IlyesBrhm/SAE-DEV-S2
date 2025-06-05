package universite_paris8.iut.youadah.projet.vue;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class CoeurVue {
    private final HBox barreVie;
    private final Image coeur5;
    private final Image coeur4;
    private final Image coeur3;
    private final Image coeur2;
    private final Image coeur1;
    private final Image coeur0;

    private final Image bouclier5;
    private final Image bouclier4;
    private final Image bouclier3;
    private final Image bouclier2;
    private final Image bouclier1;
    private final Image bouclier0;

    private final ImageView coeurView;
    private int pv;
    private boolean estArmure;

    public CoeurVue(int pv, boolean estArmure) {
        this.pv = pv;
        this.estArmure = estArmure;
        this.barreVie = new HBox(5);
        this.coeur5 = new Image(getClass().getResource("/images/heart5.png").toExternalForm());
        this.coeur4 = new Image(getClass().getResource("/images/heart4.png").toExternalForm());
        this.coeur3 = new Image(getClass().getResource("/images/heart3.png").toExternalForm());
        this.coeur2 = new Image(getClass().getResource("/images/heart2.png").toExternalForm());
        this.coeur1 = new Image(getClass().getResource("/images/heart1.png").toExternalForm());
        this.coeur0 = new Image(getClass().getResource("/images/heart0.png").toExternalForm());

        this.bouclier5 = new Image(getClass().getResource("/images/bouclier5.png").toExternalForm());
        this.bouclier4 = new Image(getClass().getResource("/images/bouclier4.png").toExternalForm());
        this.bouclier3 = new Image(getClass().getResource("/images/bouclier3.png").toExternalForm());
        this.bouclier2 = new Image(getClass().getResource("/images/bouclier2.png").toExternalForm());
        this.bouclier1 = new Image(getClass().getResource("/images/bouclier1.png").toExternalForm());
        this.bouclier0 = new Image(getClass().getResource("/images/bouclier0.png").toExternalForm());


        this.coeurView = new ImageView();
        coeurView.setFitWidth(32);
        coeurView.setFitHeight(32);
        barreVie.getChildren().add(coeurView);

        mettreAJourPv(pv); // Initialisation
    }

    public void mettreAJourPv(int pv) {
        this.pv = pv;
        switch (pv) {
            case 5 -> coeurView.setImage(estArmure ? bouclier5 : coeur5);
            case 4 -> coeurView.setImage(estArmure ? bouclier4 : coeur4);
            case 3 -> coeurView.setImage(estArmure ? bouclier3 : coeur3);
            case 2 -> coeurView.setImage(estArmure ? bouclier2 : coeur2);
            case 1 -> coeurView.setImage(estArmure ? bouclier1 : coeur1);
            case 0 -> coeurView.setImage(estArmure ? bouclier0 : coeur0);
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