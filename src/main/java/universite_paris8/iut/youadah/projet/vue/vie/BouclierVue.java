package universite_paris8.iut.youadah.projet.vue.vie;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

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

    public void afficherBouclier() {
        pane.getChildren().add(bouclierView);
    }
}