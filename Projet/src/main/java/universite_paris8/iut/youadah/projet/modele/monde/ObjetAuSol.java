package universite_paris8.iut.youadah.projet.modele.monde;

import javafx.scene.layout.Pane;
import universite_paris8.iut.youadah.projet.modele.objet.Objet;
import universite_paris8.iut.youadah.projet.vue.ObjetVue;

public class ObjetAuSol {
    private static final int TAILLE_TUILE = 32;

    private final int xTuile;
    private final int yTuile;
    private Objet objet;
    private int quantite; // ✅ Ajout de la quantité
    private final ObjetVue objetVue;

    // Constructeur avec quantité par défaut = 1
    public ObjetAuSol(int xTuile, int yTuile, Pane pane, Objet objet) {
        this(xTuile, yTuile, pane, objet, 1);
    }

    // Constructeur avec quantité personnalisée
    public ObjetAuSol(int xTuile, int yTuile, Pane pane, Objet objet, int quantite) {
        this.xTuile = xTuile;
        this.yTuile = yTuile;
        this.objet = objet;
        this.quantite = quantite; // ✅ Initialisation
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
    public Objet getObjet() { return objet; }
    public int getQuantite() { return quantite; } // ✅ Getter pour la quantité

    public void setQuantite(int quantite) { // ✅ Setter optionnel
        this.quantite = Math.max(0, quantite);
    }
}