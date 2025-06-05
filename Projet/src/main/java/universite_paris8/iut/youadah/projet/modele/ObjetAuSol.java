package universite_paris8.iut.youadah.projet.modele;

import javafx.scene.layout.Pane;
import universite_paris8.iut.youadah.projet.vue.ObjetVue;

import java.util.ArrayList;

public class ObjetAuSol {
    private final int TAILLE_TUILE = 32;
    private static final ArrayList<ObjetAuSol> objetsAuSol = new ArrayList<>();

    private int x, y;
    private ObjetVue objetVue;

    public ObjetAuSol(int x, int y, Pane pane, Objet objet) {
        this.x = x;
        this.y = y;
        this.objetVue = new ObjetVue(objet);

        objetVue.getImageView().setFitWidth(26);
        objetVue.getImageView().setFitHeight(26);
        objetVue.getImageView().setLayoutX(x * TAILLE_TUILE);
        objetVue.getImageView().setLayoutY(y * TAILLE_TUILE);

        objetsAuSol.add(this);
        pane.getChildren().add(objetVue.getImageView());
    }

    public static void ramasser(Player joueur, Inventaire inventaire, Pane pane) {
        int joueurX = (int) (joueur.getX() / 32);
        int joueurY = (int) (joueur.getY() / 32);

        ArrayList<ObjetAuSol> aRamasser = new ArrayList<>();

        for (ObjetAuSol o : objetsAuSol) {
            if (o.x == joueurX && o.y == joueurY) {
                inventaire.ajouterObjet(o.objetVue.getObjet());
                pane.getChildren().remove(o.objetVue.getImageView());
                aRamasser.add(o);
            }
        }

        objetsAuSol.removeAll(aRamasser);
    }

    public static void deposerJoueur(Objet objet, Player joueur, Pane pane) {
        int x = (int) (joueur.getX() / 32);
        int y = (int) (joueur.getY() / 32);

        new ObjetAuSol(x, y, pane, objet);
    }

    public static void deposer(Objet objet, int x, int y, Pane pane) {
        new ObjetAuSol(x, y, pane, objet);
    }
}
