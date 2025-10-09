package universite_paris8.iut.youadah.projet.modele;

import javafx.scene.layout.Pane;
import universite_paris8.iut.youadah.projet.vue.ObjetVue;

public class ObjetAuSol {
    private static final int TAILLE_TUILE = 32;

    private final int xTuile;
    private final int yTuile;
    private final ObjetVue objetVue;

    public ObjetAuSol(int xTuile, int yTuile, Pane pane, Objet objet) {
        this.xTuile = xTuile;
        this.yTuile = yTuile;
        this.objetVue = new ObjetVue(objet);

        objetVue.getImageView().setFitWidth(26);
        objetVue.getImageView().setFitHeight(26);
        objetVue.getImageView().setLayoutX(xTuile * TAILLE_TUILE);
        objetVue.getImageView().setLayoutY(yTuile * TAILLE_TUILE);

        pane.getChildren().add(objetVue.getImageView());
    }

    public int getXTuile() { return xTuile; }
    public int getYTuile() { return yTuile; }
    public ObjetVue getObjetVue() { return objetVue; }

    /** Pratique pour l’inventaire */
    public Objet getObjet() { return objetVue.getObjet(); }
}
